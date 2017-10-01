package team.yqby.platform.base.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class OrderRes {
    private String orderNo;
    private String state;
    private String price;
    private String deliverType;
    private String freightAmt;
    private String createTime;
    private String putOrderTime;
    private Long addressId;
    private Long shopId;
    private String address;
    private List<ImagesRes> images;
}
