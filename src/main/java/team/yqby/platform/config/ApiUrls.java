package team.yqby.platform.config;

/**
 * 接口地址
 * @author jumping
 * @date 2015/11/16
 */
public class ApiUrls {

    //登陆
    public static final String LOGIN="/login";

    //查询用户编号
    public static final String QUERY_OPEN_ID="/queryOpenID";

    //查询支付签名
    public static final String PAY_SIGN="/paySign";

    //创建支付订单
    public static final String CREATE_ORDER_URL="/createOrder";

    //确认订单信息
    public static final String CONFIRM_ORDER_URL="/confirmOrder";

    //支付回调通知
    public static final String PAY_NOTIFY_URL="/payCallBack";

    //上传图片
    public static final String UPLOAD_PIC="/uploadPic";

    //上传多张图片
    public static final String UPLOAD_MULTIPLE_PIC="/uploadMultiplePic";

    //删除图片
    public static final String DELETE_PIC="/deletePic";

    //查询商品价格
    public static final String QUERY_GOODS_PRICE="/queryGoodsPrice";

    //查询收货地址
    public static final String QUERY_ADDRESS = "/queryAddress";

    //删除收获地址
    public static final String DEL_ADDRESS = "/delAddress";

    //添加收货地址
    public static final String ADD_ADDRESS = "/addAddress";

    //编辑收货地址
    public static final String EDIT_ADDRESS = "/editAddress";

    //查询门店信息
    public static final String QUERY_SHOP = "/queryShop";

    //新增门店信息
    public static final String ADD_SHOP = "/addShop";

    //设置收货地址
    public static final String SET_DEFAULT_ADDRESS = "/setDefaultAddress";

    //查询订单
    public static final String QUERY_ORDER = "/queryOrder";

    //查询所有订单
    public static final String QUERY_ALL_ORDER = "/queryAllOrder";

    //查询订单明细
    public static final String QUERY_DETAIL = "/queryDetail";

    //更新订单信息(发货)
    public static final String ORDER_DELIVERY = "/orderDelivery";

}
