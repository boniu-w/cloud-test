package  wgcloud.test1.application.service.impl;

import org.springframework.stereotype.Service;
import  wgcloud.test1.application.service.AspectService;

/**
 * @author wg
 * @Package wg.application.service.impl
 * @date 2020/4/28 13:44
 * @Copyright
 */
@Service
public class AspectServiceImpl implements AspectService {


    @Override
    public Object add(String userName) {

        System.out.println("AspectServiceImpl userName: "+userName);
        return null;
    }
}
