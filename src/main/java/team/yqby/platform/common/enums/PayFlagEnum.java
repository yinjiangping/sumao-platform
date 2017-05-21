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

    N("N", "未支付"),
    Y("Y", "已支付");

    private String code;

    private String desc;

}
