package team.yqby.platform.base.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class FlowOpenIDRes {

    @JSONField(name = "errcode")
    private String errcode;

    @JSONField(name = "errmsg")
    private String errmsg;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private int expiresIn;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "openid")
    private String openid;

    @JSONField(name = "scope")
    private String scope;

    @JSONField(name = "unionid")
    private String unionid;

}
