package retailer.reward.system.repository;

import retailer.reward.system.RetailerRewardSystemApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RetailerRewardSystemApplication.class})
class TransactionsRepositoryTest {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Test
    public void test_findByUserId_success_scenario(){

        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        transactionsRepository.save(rewardsAndPaymentsEntity);
        List<TransactionsEntity> returnresponse = transactionsRepository.findByUserId(1l);
        assertEquals(25l, returnresponse.get(0).getRewardPoints());
    }

    @Test
    public void test_findByUserId_failure_scenario(){
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        transactionsRepository.save(rewardsAndPaymentsEntity);
        List<TransactionsEntity> returnresponse = transactionsRepository.findByUserId(2l);
        assertEquals(0, returnresponse.size());
    }

    @Test
    public void test_findTotalRewardPoints_success_scenario(){
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        transactionsRepository.save(rewardsAndPaymentsEntity);
        Long rewardPoints = transactionsRepository.findTotalRewardPoints(1l, LocalDate.now());
        assertEquals(25l, rewardPoints);
    }

    @Test
    public void test_findTotalRewardPoints_failure_scenario(){
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        transactionsRepository.save(rewardsAndPaymentsEntity);
        Long rewardPoints = transactionsRepository.findTotalRewardPoints(2l, LocalDate.now());
        assertEquals(null, rewardPoints);
    }

    @Test
    public void test_findTotalFrequencyRewardPoints_success_scenario(){
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        TransactionsEntity rewardsAndPaymentsEntity1 = new TransactionsEntity(2l, 1l, "Jackfruit", 1, 75.0, 25l, LocalDate.now().minusMonths(1));
        TransactionsEntity rewardsAndPaymentsEntity2 = new TransactionsEntity(3l, 1l, "Muskmelon", 1, 75.0, 25l, LocalDate.now().minusDays(15));
        transactionsRepository.saveAll(Arrays.asList(rewardsAndPaymentsEntity, rewardsAndPaymentsEntity1, rewardsAndPaymentsEntity2));
        Long rewardPoints = transactionsRepository.findTotalFrequencyRewardPoints(1l, LocalDate.now(), LocalDate.now().minusMonths(2));
        assertEquals(75l, rewardPoints);
    }

    @Test
    public void test_findTotalFrequencyRewardPoints_failure_scenario(){
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "watermelon", 1, 75.0, 25l, LocalDate.now());
        TransactionsEntity rewardsAndPaymentsEntity1 = new TransactionsEntity(2l, 1l, "Jackfruit", 1, 75.0, 25l, LocalDate.now().minusMonths(1));
        TransactionsEntity rewardsAndPaymentsEntity2 = new TransactionsEntity(3l, 1l, "Muskmelon", 1, 75.0, 25l, LocalDate.now().minusDays(15));
        transactionsRepository.saveAll(Arrays.asList(rewardsAndPaymentsEntity, rewardsAndPaymentsEntity1, rewardsAndPaymentsEntity2));
        Long rewardPoints = transactionsRepository.findTotalFrequencyRewardPoints(2l, LocalDate.now(), LocalDate.now().minusMonths(2));
        assertEquals(null, rewardPoints);
    }
}