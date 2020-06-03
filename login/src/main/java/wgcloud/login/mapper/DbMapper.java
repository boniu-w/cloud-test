package wgcloud.login.mapper;

import org.springframework.stereotype.Component;
import wgcloud.login.entity.User;

import java.util.HashSet;
import java.util.Set;

/********************************************************
 * @Package wgcloud.login.mapper
 * @author wg
 * @date 2020/6/3 16:04
 * @version
 * @Copyright
 ********************************************************/
public class DbMapper {
    private static Set<User> dbuserSet = new HashSet<>();

    static {
        dbuserSet.add(new User("1", "xiaohei", "123"));
        dbuserSet.add(new User("2", "展昭", "1111"));
        dbuserSet.add(new User("3", "小白", "2222"));
    }

    public static Set<User> getDbuserSet() {
        return dbuserSet;
    }
}
