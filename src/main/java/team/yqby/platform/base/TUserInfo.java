package team.yqby.platform.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class TUserInfo {
    private Long id;

    private String password;

    private String username;

    private Long shopId;
}
