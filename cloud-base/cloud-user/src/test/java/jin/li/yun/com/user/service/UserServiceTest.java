package jin.li.yun.com.user.service;

import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2021/07/12
 */
@Slf4j
@SpringBootTest
@EnableTransactionManagement
@RunWith(SpringRunner.class)
public class UserServiceTest {
  @Resource private UserService userService;

    @Test
    public void save() {
        UserPoJo userPoJo = UserPoJo.builder()
                .name("张三")
                .avatar("http://223.jpg")
                .password(DigestUtils.md5DigestAsHex("123456".getBytes()))
                .tel("15675458912")
                .status(1)
                .build();
        boolean saveUser = userService.saveUser(userPoJo);
        Assert.assertTrue(saveUser);
        UserPoJo find = userService.getById(userPoJo);
        Assert.assertNotNull(find);
    }
}
