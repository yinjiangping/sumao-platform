package team.yqby.platform.base.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressRes {
    /**
     * 收件人地址
     */
    private String rAddress;
    /***
     * 收件人姓名
     */
    private String rUserName;
    /**
     * 收件人联系方式
     */
    private String rPhone;
    /**
     * 邮编号
     */
    private String zipCode;
    /**
     * 是否默认
     */
    private String isDefault;
}
