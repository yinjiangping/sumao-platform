package team.yqby.platform.base.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class AddressReq {
    /**
     * 用户编号
     */
    private String openID;
    /**
     * 收件信息编号
     */
    private Long addressId;
    /**
     * 发件人地址
     */
    private String sAddress;
    /**
     * 发件人姓名
     */
    private String sUserName;
    /**
     * 发件人联系方式
     */
    private String sPhone;
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
