package team.yqby.platform.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCodeEnum {

    ILLEGAL_DATA("ILLEGAL_DATA", "亲，数据有误，请重新输入..."),
    DATABASE_SELECT_IS_NULL("DATABASE_SELECT_IS_NULL", "亲，正在努力查询数据，请稍后..."),
    SYSTEM_ERROR("SYSTEM_ERROR", "亲，系统维护中，请稍后...");

    private String code;

    private String desc;

}
