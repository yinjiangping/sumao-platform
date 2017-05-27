package team.yqby.platform.common.enums;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public enum PicPriceType {
    pri_price_6(6, "c6", "0.99", "6寸照片价格"),
    pri_price_8(8, "c8", "2.49", "8寸照片价格"),
    pri_price_12(12, "c12", "12.49", "12寸照片价格"),
    pri_price_18(18, "c18", "52.49", "18寸照片价格");

    //图片大小
    private int picSize;
    //图片标志
    private String picMark;
    //图片单价
    private String picPrice;
    //图片描述
    private String desc;

    /**
     * 获取照片价格
     *
     * @param picSize 图片大小
     * @return
     */
    public static String getPicPrice(int picSize) {
        for (PicPriceType picPriceType : PicPriceType.values()) {
            if (Objects.equal(picPriceType.getPicSize(), picSize)) {
                return picPriceType.getPicPrice();
            }
        }
        return null;
    }

}
