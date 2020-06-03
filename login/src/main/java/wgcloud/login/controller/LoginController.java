package wgcloud.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wgcloud.login.entity.User;
import wgcloud.login.mapper.DbMapper;

import javax.servlet.http.HttpSession;
import java.util.*;

/********************************************************
 * @Package wgcloud.login.controller
 * @author wg
 * @date 2020/6/3 15:24
 * @version
 * @Copyright
 ********************************************************/
@Controller
@RequestMapping(value = "/loginModule/loginController")
public class LoginController {

    private Set<User> dbuserSet = DbMapper.getDbuserSet();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String booleanLogin(User user, HttpSession session) {
        System.out.println("---------------"+user.getUserName());

        String target = (String) session.getAttribute("target");
        Optional<User> first = dbuserSet.stream().filter(dbuser -> dbuser.getUserName().equals(user.getUserName())
          && dbuser.getPassword().equals(user.getPassword())).findFirst();


        HashMap<String, Object> hashMap = new HashMap<>();

        if (first.isPresent()) {
            System.out.println(first.get().toString());


            String token = UUID.randomUUID().toString();
            hashMap.put(token, first.get());

            return "redirect" + target;
        } else {

            return "登录失败";
        }
    }
}
