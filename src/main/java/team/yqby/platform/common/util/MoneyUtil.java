package team.yqby.platform.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class MoneyUtil {
    private static final Logger log = LoggerFactory.getLogger(MoneyUtil.class);

    /**
     * 将分为单位的转换为元 (除100)
     * @param amount
     * @return
     */
    public static String changeF2Y(String amount) {
        try {
            String temp = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();

            if (temp.indexOf(".")>0 ) {
                if (StringUtils.split(temp,".")[1].length() == 1) {
                    temp += "0";
                }
            }else {
                temp += ".00";
            }
            return temp;
        } catch (Exception e) {
            log.error("金额:{},分转换成元异常:{}",amount,e);
        }
        return null;
    }

    /**
     * 将元为单位的转换为分 (乘100)
     * @param amount
     * @return
     */
    public static String changeY2F(String amount){

        if (StringUtils.isBlank(amount)) {
            return "";
        }
        String temp = amount.trim();
        int postion = -100;
        postion = temp.indexOf(".");
        if (postion != 0) {
            BigDecimal inhead = new BigDecimal(0);
            if (postion != -1) {
                BigDecimal head = new BigDecimal(temp.substring(0, postion));
                String foots = temp.substring(postion + 1, temp.length());
                if (foots.length() >= 3) {
                    inhead = new BigDecimal(foots.substring(0, 2));
                    temp = (head.multiply(new BigDecimal(100)).add(inhead))
                            .toString();
                    return temp;
                } else if (foots.length() == 2) {
                    temp = (head.multiply(new BigDecimal(100))
                            .add(new BigDecimal(foots))).toString();
                    return temp;
                } else {
                    temp = (head.multiply(new BigDecimal(100))
                            .add(new BigDecimal(foots).multiply(new BigDecimal(
                                    10)))).toString();
                    return temp;
                }
            } else {
                BigDecimal head = new BigDecimal(temp);
                temp = head.multiply(new BigDecimal(100)).toString();
                return temp;
            }
        } else {
            BigDecimal head = new BigDecimal(temp);
            temp = head.multiply(new BigDecimal(100)).toString();
            return temp;
        }
    }


}
