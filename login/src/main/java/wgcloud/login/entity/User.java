package wgcloud.login.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/********************************************************
 * @Package wgcloud.login.entity
 * @author wg
 * @date 2020/6/3 15:26
 * @version
 * @Copyright
 ********************************************************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String userName;
    private String password;

}
