package retailer.reward.system.service;

import retailer.reward.system.RetailerRewardSystemApplication;
import retailer.reward.system.exception.BadRequestException;
import retailer.reward.system.exception.UnHandeledEntityException;
import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.ResponseState;
import retailer.reward.system.model.TransactionRequest;
import retailer.reward.system.repository.TransactionsEntity;
import retailer.reward.system.repository.TransactionsRepository;
import retailer.reward.system.repository.UserEntity;
import retailer.reward.system.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RetailerRewardSystemApplication.class})
class TransactionServiceImplTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionsRepository transactionsRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void saveTransactionSuccessTest() {
        UserEntity userEntity = new UserEntity(null, "uma");
        UserEntity userEntityReturn = new UserEntity(1l, "uma");
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntityReturn);
        TransactionRequest transactionRequest = new TransactionRequest("uma", "Laptop", 150.0, 1);
        TransactionsEntity saveRewardsAndPaymentsEntity = new TransactionsEntity(null, 1l, "Laptop", 1, 150.0, 150l, LocalDate.now());
        TransactionsEntity returnTransactionsEntity = new TransactionsEntity(1l, 1l, "Laptop", 1, 150.0, 150l, LocalDate.now());
        Mockito.when(transactionsRepository.save(saveRewardsAndPaymentsEntity)).thenReturn(returnTransactionsEntity);
        ResponseDto<TransactionsEntity> responseDto = transactionService.saveTransaction(transactionRequest);
        Assertions.assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(150, responseDto.getData().getRewardPoints());
    }

    @Test
    public void saveTransactionErrorTest() {
        assertThrows(BadRequestException.class, () -> transactionService.saveTransaction(null));
    }

    @Test
    public void getTransactionSuccessTest() {
        UserEntity userEntity = new UserEntity(1l, "uma");
        Mockito.when(userRepository.findByUserName("uma")).thenReturn(userEntity);
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "mobile", 1, 75.0, 25l, LocalDate.now());
        Mockito.when(transactionsRepository.findByUserId(1l)).thenReturn(Arrays.asList(rewardsAndPaymentsEntity));
        ResponseDto<List<TransactionsEntity>> responseDto = transactionService.getTransactions("uma");
        assertEquals(ResponseState.SUCCESS, responseDto.getResponseStatus());
        assertEquals(25, responseDto.getData().get(0).getRewardPoints());
    }

    @Test
    public void getTransactionsErrorTest() {
        TransactionsEntity rewardsAndPaymentsEntity = new TransactionsEntity(1l, 1l, "mobile", 1, 75.0, 25l, LocalDate.now());
        Mockito.when(transactionsRepository.findByUserId(1l)).thenReturn(Arrays.asList(rewardsAndPaymentsEntity));
        assertThrows(UnHandeledEntityException.class, () -> transactionService.getTransactions("uma"));
    }

    @Test
    public void findUserIdSuccessTest() {
        UserEntity userEntity = new UserEntity(1l, "uma");
        Mockito.when(userRepository.findByUserName("uma")).thenReturn(userEntity);
        Long userId = transactionService.findUserId("uma");
        assertEquals(1, userId);
    }

    @Test
    public void findUserIdInSaveSuccessTest() {
        UserEntity userEntity = new UserEntity(null, "uma");
        UserEntity userEntityReturn = new UserEntity(1l, "uma");
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntityReturn);
        Long userId = transactionService.findUserId("uma");
        assertEquals(1, userId);
    }

    @Test
    public void findUserIdInSaveErrorTest() {
        assertThrows(UnHandeledEntityException.class, () -> transactionService.findUserId("uma"));
    }
}