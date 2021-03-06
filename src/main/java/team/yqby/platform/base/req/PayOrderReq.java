package team.yqby.platform.base.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class PayOrderReq implements Serializable {

    private static final long serialVersionUID = 7941220567026640733L;

   //用户微信唯一标识
    private String openID;

    //订单金额
    private Long orderAmt;

    //文件编号列表
    private String fileIds;

}
