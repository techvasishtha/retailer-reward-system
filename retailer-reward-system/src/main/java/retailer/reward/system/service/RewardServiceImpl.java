package retailer.reward.system.service;

import retailer.reward.system.exception.NoRecordsFoundException;
import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.Rewards;
import retailer.reward.system.repository.TransactionsRepository;
import retailer.reward.system.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService{

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     *  Description: For the given userName find the transactions and summarize the total reward points per day and returns the total points on daily basis.
     * @param userName
     * @return ResponseDto<Rewards>
     */
    @Override
    public ResponseDto<Rewards> getRewards(String userName) {
        Long totalRewards = null;
        try{
            totalRewards = transactionsRepository.findTotalRewardPoints(transactionService.findUserId(userName), LocalDate.now());
        }catch (Exception e){
            log.error("getTotalRewardForUserPerDay(), No records Found with UserName :: {}",userName);
            throw new NoRecordsFoundException("No records Found with UserName"+userName);
        }
        return ResponseDto.forSuccess(new Rewards(Constants.DAILY, totalRewards, userName));
    }

    /**
     *  Description: For the given userName find the transactions and summarize the total reward points per month and returns the total points on monthly basis.
     * @param userName
     * @return ResponseDto<Rewards>
     */
    @Override
    public ResponseDto<Rewards> getRewardsByMonthly(String userName) {
        Long totalRewards = null;
        try{
         totalRewards = transactionsRepository.findTotalFrequencyRewardPoints(transactionService.findUserId
                (userName), LocalDate.now(), LocalDate.now().minusMonths(1));
        }catch (Exception e){
            log.error("getMonthlyRewardsForUser(), No records Found with UserName :: {}",userName);
            throw new NoRecordsFoundException("No records Found with UserName"+userName);
        }
        return ResponseDto.forSuccess(new Rewards(Constants.MONTHLY, totalRewards, userName));
    }

    /**
     *  Description: For the given userName find the transactions and summarize the total reward points per quarterly and returns the total points on quarterly basis.
     * @param userName
     * @return ResponseDto<Rewards>
     */
    @Override
    public ResponseDto<Rewards> getRewardsByQuarterly(String userName) {
        Long totalRewards = null;
        try{
          totalRewards = transactionsRepository.findTotalFrequencyRewardPoints(transactionService.findUserId(userName), LocalDate.now(), LocalDate.now().minusMonths(3));
        }catch (Exception e){
            log.error("getQuarterlyRewardsForUser(), No records Found with UserName :: {}",userName);
            throw new NoRecordsFoundException("No records Found with UserName"+userName);
        }
        return ResponseDto.forSuccess(new Rewards(Constants.QUARTERLY, totalRewards, userName));
    }


    /**
     *  Description: Delete  all transactions on daily basis older than 3 months.
     */
    @Override
    public void deleteQuarterlyTransactions(){
        log.info("Clean transactions for the older than date {} was triggered..",LocalDate.now().minusMonths(3));
        transactionsRepository.deleteByCreatedDateBefore(LocalDate.now().minusMonths(3));
    }
}
