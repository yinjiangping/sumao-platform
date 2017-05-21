package team.yqby.platform.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ArchiveFlagEnum {

    STR_0("0", "有效"),
    STR_1("1", "无效");

    private String code;

    private String desc;

}
