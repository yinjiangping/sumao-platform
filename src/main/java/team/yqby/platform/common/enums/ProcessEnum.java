package team.yqby.platform.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessEnum {

    INIT("1", "初始状态"),
    WAIT_PAY("2", "待支付"),
    ORDER_SUCCESS("3", "下单成功"),
    ORDER_FAIL("4", "下单失败"),
    PAY_SUCCESS("5", "支付失败"),
    PAY_FAIL("6", "支付失败"),
    BUSINESS_SUCCESS("7", "已发货"),
    BUSINESS_FAIL("8", "发货失败");

    private String code;

    private String desc;

}
