package retailer.reward.system.config;

import retailer.reward.system.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TransactionCleaningScheduler {

    @Autowired
    private RewardService rewardService;

    @Scheduled(cron = "0 0 0 * * *")
    public void purgeOlderThanQuarterRewardsAndPayments() {
        rewardService.deleteQuarterlyTransactions();
    }
}
