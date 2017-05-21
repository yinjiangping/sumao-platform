package team.yqby.platform.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum PayFlagEnum {

    STR_0("0", "未支付"),
    STR_1("1", "已支付");

    private String code;

    private String desc;

}
