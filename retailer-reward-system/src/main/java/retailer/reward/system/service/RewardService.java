package retailer.reward.system.service;

import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.Rewards;

public interface RewardService {
    ResponseDto<Rewards> getRewards(String userName);
    ResponseDto<Rewards> getRewardsByMonthly(String userName);
    ResponseDto<Rewards> getRewardsByQuarterly(String userName);
    void deleteQuarterlyTransactions();

}
