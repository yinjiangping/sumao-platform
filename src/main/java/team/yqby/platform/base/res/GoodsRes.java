package team.yqby.platform.base.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter
@ToString
public class GoodsRes {
    //运费
    private String freightAmt;
    //商品信息
    private Map<String, String> goods;
}
