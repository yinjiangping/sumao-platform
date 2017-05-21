package team.yqby.platform.base.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.XStream;
import lombok.*;
import team.yqby.platform.common.util.XStreamUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayConfirmRes {

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
