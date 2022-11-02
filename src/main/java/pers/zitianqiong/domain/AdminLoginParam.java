package pers.zitianqiong.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/11/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
//具体的作用是开启链式编程，让我们写代码更加方便。
@Accessors(chain = true)
public class AdminLoginParam {
    private String username;
    private String password;
    private String code;
}
