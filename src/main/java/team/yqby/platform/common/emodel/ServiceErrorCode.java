package team.yqby.platform.common.emodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 错误枚举
 * </p>
 * User：jumping Date： 2016/11/5 0005 Version：1.0
 */
@Getter
@AllArgsConstructor
public enum ServiceErrorCode {
    ERROR_CODE_A10001("A10001", "商品信息不存在"),
    ERROR_CODE_A10002("A10002", "支付金额有误，请重新下单"),
    ERROR_CODE_A10003("A10003", "下单失败"),
    ERROR_CODE_A10004("A10004", "参数错误"),
    ERROR_CODE_A10005("A10005", "签名有误"),
    ERROR_CODE_A10006("A10006", "未找到订单信息"),
    ERROR_CODE_A10007("A10007", "数据库异常"),
    ERROR_CODE_A10008("A10008", "获取签名失败"),
    ERROR_CODE_A10009("A10009", "用户未登陆"),
    ERROR_CODE_A10010("A10010", "订单已支付成功"),
    ERROR_CODE_A10011("A10011", "订单已发货"),
    ERROR_CODE_A20001("A20001", "收货地址有误"),
    ERROR_CODE_A20002("A20002", "订单信息被篡改"),
    ERROR_CODE_A20003("A20003", "操作失败"),
    ERROR_CODE_A20004("A20004", "收获地址已存在"),
    ERROR_CODE_A20005("A20005", "默认收获地址已设置"),
    ERROR_CODE_A20006("A20006", "收获地址不存在"),
    ERROR_CODE_A20007("A20007", "收获地址无法删除"),

    ERROR_CODE_A10012("A10012", "用户名不存在"),
    ERROR_CODE_A10013("A10013", "用户名或密码有误"),

    ERROR_CODE_F99999("F99999", "服务器繁忙，请稍后重试!"),
    ERROR_CODE_F88888("F88888", "正在受理请求中"),
    ERROR_CODE_F777777("F77777", "当前无法操作");
    private String resCode;
    private String resDesc;
}
