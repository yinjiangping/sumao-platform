package team.yqby.platform.base.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter
@ToString
public class OrderDetailRes {
    private String orderNo;
    private String openID;
    private String orderAmt;
    private String putOrderTime;
    private String state;
    private String receiveAddress;
    private String receivePhone;
    private String receiveName;
    private String sendAddress;
    private String sendPhone;
    private String sendName;
    private String expressInfo;
    private String resCode;
    private String resDesc;
    private List<ImagesRes> imagesResList;
}
