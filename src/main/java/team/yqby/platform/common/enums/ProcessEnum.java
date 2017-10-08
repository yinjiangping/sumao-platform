package team.yqby.platform.common.enums;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessEnum {

    INIT("INIT", "初始状态"),
    WAIT_PAY("WAIT_PAY", "下单成功"),
    ORDER_FAIL("ORDER_FAIL", "下单失败"),
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
    PAY_FAIL("PAY_FAIL", "支付失败"),
    BEING_MADE("BEGIN_MADE", "正在制作"),
    DELIVERY_SUCCESS("DELIVERY_SUCCESS", "已发货"),
    DELIVERY_FAIL("DELIVERY_FAIL", "发货失败");

    private String code;

    private String desc;

    /**
     * 获取订单状态
     *
     * @param status 状态
     * @return
     */
    public static String getOrderStatus(String status) {
        for (ProcessEnum processEnum : ProcessEnum.values()) {
            if (Objects.equal(processEnum.getCode(), status)) {
                return processEnum.getDesc();
            }
        }
        return null;
    }
}
