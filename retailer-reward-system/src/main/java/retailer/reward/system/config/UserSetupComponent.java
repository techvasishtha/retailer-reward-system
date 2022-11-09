package retailer.reward.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retailer.reward.system.repository.UserEntity;
import retailer.reward.system.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class UserSetupComponent {

    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    public void setUpDefaultUsers() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("Uma");
        userRepository.save(userEntity);
        userEntity.setUserName("Jeswanth");
        userRepository.save(userEntity);
        userEntity.setUserName("Lakshmi Shashank");
        userRepository.save(userEntity);

    }
}
