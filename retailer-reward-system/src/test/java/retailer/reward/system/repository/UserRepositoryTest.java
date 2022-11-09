package retailer.reward.system.repository;

import retailer.reward.system.RetailerRewardSystemApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RetailerRewardSystemApplication.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserNameSuccess_test() {
        UserEntity userEntity = new UserEntity(null, "uma");
        userRepository.save(userEntity);
        UserEntity returnUserEntity = userRepository.findByUserName("uma");
        assertEquals("uma", returnUserEntity.getUserName());
    }

    @Test
    void findByUserName_failure_test() {
       assertEquals(null, userRepository.findByUserName("jeswanth"));
    }
}