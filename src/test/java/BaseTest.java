import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import team.yqby.platform.mapper.TDeliveryAddressMapper;
import team.yqby.platform.pojo.TDeliveryAddress;

/**
 * 单元测试基类
 *
 * @author tiger
 * @version 1.0.0 createTime: 15/6/28 下午12:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-extra.xml")
@WebAppConfiguration
@Transactional
public class BaseTest extends TestCase {

    @Autowired(required = false)
    private TDeliveryAddressMapper tDeliveryAddressMapper;
       @Test
        public void test(){
           TDeliveryAddress tDeliveryAddress = new TDeliveryAddress();
           tDeliveryAddress.setCustomerId("1111111111111");
           tDeliveryAddress.setDeliveryAddress("上海市徐汇区萨德小区");
           tDeliveryAddress.setDeliveryName("张三");
           tDeliveryAddress.setDeliveryTel("12312311312");
           tDeliveryAddress.setMailNumber("121312");
           int j = tDeliveryAddressMapper.insertSelective(tDeliveryAddress);
           System.out.println(tDeliveryAddress.getId() + "--"+j);
        }
}
