package retailer.reward.system.service;

import retailer.reward.system.RetailerRewardSystemApplication;
import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.ResponseState;
import retailer.reward.system.model.Rewards;
import retailer.reward.system.repository.TransactionsRepository;
import retailer.reward.system.constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RetailerRewardSystemApplication.class})
class RewardServiceImplTest {

    @Autowired
    private RewardService rewardService;

    @MockBean
    private TransactionsRepository transactionsRepository;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getRewardsSuccessTest(){
        Mockito.when(transactionService.findUserId("uma")).thenReturn(1l);
        Mockito.when(transactionsRepository.findTotalRewardPoints(1l, LocalDate.now())).thenReturn(150l);

        ResponseDto<Rewards> responseDto= rewardService.getRewards("uma");
        Assertions.assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(150, responseDto.getData().getRewardPoints());
        Assertions.assertEquals(Constants.DAILY, responseDto.getData().getRewardFrequency());
    }

    @Test
    public void getRewardsErrorTest(){
        ResponseDto<Rewards> responseDto= rewardService.getRewards("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(0, responseDto.getData().getRewardPoints());
        assertEquals(Constants.DAILY, responseDto.getData().getRewardFrequency());
    }

    @Test
    public void getRewardsByMonthlySuccessTest(){
        Mockito.when(transactionService.findUserId("uma")).thenReturn(1l);
        Mockito.when(transactionsRepository.findTotalFrequencyRewardPoints(1l, LocalDate.now(), LocalDate.now().minusMonths(1))).thenReturn(300l);

        ResponseDto<Rewards> responseDto= rewardService.getRewardsByMonthly("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(150, responseDto.getData().getRewardPoints());
        assertEquals(Constants.MONTHLY, responseDto.getData().getRewardFrequency());
    }

    @Test
    public void getRewardsByMonthlyErrorTest(){
        ResponseDto<Rewards> responseDto= rewardService.getRewardsByMonthly("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(0, responseDto.getData().getRewardPoints());
        assertEquals(Constants.MONTHLY, responseDto.getData().getRewardFrequency());
    }

    @Test
    public void getRewardsByQuarterlySuccessTest(){
        Mockito.when(transactionService.findUserId("uma")).thenReturn(1l);
        Mockito.when(transactionsRepository.findTotalFrequencyRewardPoints(1l, LocalDate.now(), LocalDate.now().minusMonths(3))).thenReturn(150l);

        ResponseDto<Rewards> responseDto= rewardService.getRewardsByQuarterly("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(150, responseDto.getData().getRewardPoints());
        assertEquals(Constants.QUARTERLY, responseDto.getData().getRewardFrequency());
    }

    @Test
    public void getRewardsByQuarterlyErrorTest(){
        ResponseDto<Rewards> responseDto= rewardService.getRewardsByQuarterly("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(0, responseDto.getData().getRewardPoints());
        assertEquals(Constants.QUARTERLY, responseDto.getData().getRewardFrequency());
    }


}