package team.yqby.platform.base.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class PayOrderRes implements Serializable {

    //公众号id
    private String appId;
    //时间戳
    private Long timeStamp;
    //随机字符串
    private String nonceStr;
    //订单详情扩展字符串
    @JSONField(name = "package")
    private String package_gjz;
    //签名方式
    private String signType;
    //签名
    private String paySign;

}
