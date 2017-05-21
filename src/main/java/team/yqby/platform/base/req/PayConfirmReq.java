package team.yqby.platform.base.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayConfirmReq {
    private String openID;
    private Long  addressId;
    private String orderNo;
    private Long orderAmt;
}
