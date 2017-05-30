package team.yqby.platform.base.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter
@ToString
public class OrderDetailRes {
    private String orderNo;
    private String orderAmt;
    private String putOrderTime;
    private String state;
    private String rAddress;
    private String rPhone;
    private String rName;
    private String sAddress;
    private String sPhone;
    private String sName;
    private String expressInfo;
    private List<ImagesRes> imagesResList;
}
