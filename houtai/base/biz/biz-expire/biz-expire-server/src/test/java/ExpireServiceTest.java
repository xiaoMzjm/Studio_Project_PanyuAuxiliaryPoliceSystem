import com.base.biz.expire.server.service.ExpireService;
import com.base.biz.expire.server.service.impl.ExpireServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author:小M
 * @date:2020/7/10 1:12 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TestApplication.class)
@ActiveProfiles(profiles = {"local"})
public class ExpireServiceTest {

    @Autowired
    private ExpireService expireService;

    @Test
    public void getByTime() {

    }

    @Test
    public void createEmployeeCard() {
    }

    @Test
    public void createContract() {
    }

    @Test
    public void createRetire() {
    }
}