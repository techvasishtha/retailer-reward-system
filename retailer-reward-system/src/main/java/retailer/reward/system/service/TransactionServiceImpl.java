package retailer.reward.system.service;

import retailer.reward.system.exception.BadRequestException;
import retailer.reward.system.exception.NoRecordsFoundException;
import retailer.reward.system.exception.UnHandeledEntityException;
import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retailer.reward.system.repository.TransactionsEntity;
import retailer.reward.system.repository.TransactionsRepository;
import retailer.reward.system.repository.UserEntity;
import retailer.reward.system.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Value("${reward.threshold.min:50}")
    private Integer rewardThresholdMin;

    @Value("${reward.threshold.max:100}")
    private Integer rewardThresholdMax;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *  Description: Allows TransactionRequest after all validations from controller and fetch the userId from user table, also calculates the rewards for given entity
     *               finally if all above two scenarios executes successful saves entity into DB.
     * @param transactionRequest
     * @return ResponseDto<RewardsAndPaymentsEntity>
     * @throws Error
     */
    @Override
    public ResponseDto<TransactionsEntity> saveTransaction(TransactionRequest transactionRequest) throws Error{
        ResponseDto<TransactionsEntity> response = null;
        if(null == transactionRequest){
            throw new BadRequestException("Request body is null");
        }
        try {
            TransactionsEntity transactionsEntity = buildPaymentEntity(transactionRequest,findUserId(transactionRequest.getUserName()));
            Integer rewardPoints = calculateRewardsOnPurchase(transactionRequest);
            transactionsEntity.setRewardPoints(rewardPoints.longValue());
            response = ResponseDto.forSuccess(transactionsRepository.save(transactionsEntity));
        }catch (Exception e){
            log.error("Exception occured while save the transaction, exception is::  {}", e.fillInStackTrace());
            throw new UnHandeledEntityException("New Exception Occured"+e.getMessage());
        }

        return response;
    }

    /**
     *  Description: Returns the RewardsAndPaymentEntity for given userName if exists else throws NoRecordFoundException.
     * @param userName
     * @return ResponseDto<List<RewardsAndPaymentsEntity>>
     * @throws Error
     */
    @Override
    public ResponseDto<List<TransactionsEntity>> getTransactions(String userName) {
        List<TransactionsEntity> rewardsAndPaymentsEntityList = new ArrayList();
        try {
            rewardsAndPaymentsEntityList= transactionsRepository.findByUserId(findUserId(userName));

            if(rewardsAndPaymentsEntityList.size() == 0){
                throw new NoRecordsFoundException("No Records Found, with User :: "+userName);
            }
        }catch (Exception e){
            log.error("Exception occured while retrieving the rewardsAndPaymentsEntityList, exception is::  {}", e.fillInStackTrace());
            throw new UnHandeledEntityException("New Exception Occured"+e.getMessage());
        }
        return ResponseDto.forSuccess(rewardsAndPaymentsEntityList);
    }

    /**
     *  Description: Returns the userId for given userName & operationName.
     *              If userName exists returns it irrespective of Operation else if save operation creates userId and returns ID or else throw BadRequestException.
     * @param userName, operationName(FETCH/SAVE)
     * @return userId
     * @throws Error
     */
    @Override
    public Long findUserId(String userName){
        UserEntity userExistsEntity = userRepository.findByUserName(userName.toLowerCase());
        Long userId = null;
        try{
            if(null != userExistsEntity){
                userId= userExistsEntity.getId();
            }else {
                throw new BadRequestException("No user found with name :: "+userName);
            }
        }catch (BadRequestException e){
            log.error("BadRequest exception:: {}", e.fillInStackTrace());
            throw new BadRequestException("No user found with name :: "+userName);
        }catch (Exception e){
            log.error("Exception Occured at provideUserId () :: {}", e.fillInStackTrace());
            throw new UnHandeledEntityException("New Exception Occured"+e.getMessage());
        }
        return userId;
    }

    /**
     * Description: bind the TransactionRequest & userId to RewardsAndPaymentsEntity and returns the object.
     * @param transactionRequest
     * @param userId
     * @return RewardsAndPaymentsEntity
     */
    private TransactionsEntity buildPaymentEntity(TransactionRequest transactionRequest, Long userId){
        TransactionsEntity transactionsEntity = new TransactionsEntity();
        transactionsEntity.setUserId(userId);
        transactionsEntity.setProductName(transactionRequest.getProductName());
        transactionsEntity.setProductQuantity(transactionRequest.getProductQuantity());
        transactionsEntity.setProductPrice(transactionRequest.getProductPrice());
        transactionsEntity.setCreatedDate(LocalDate.now());
        return transactionsEntity;
    }

    /**
     * Description: Calculates 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction.
     * @param transactionRequest
     * @return RewardPoints
     */
    private Integer calculateRewardsOnPurchase(TransactionRequest transactionRequest){
        Integer rewardPoints = 0;
        if(transactionRequest.getProductPrice() > 0.0 && transactionRequest.getProductQuantity() > 0 ){
            try{
                Double totalPurchaseAmount = transactionRequest.getProductPrice() * transactionRequest.getProductQuantity();
                if( totalPurchaseAmount > rewardThresholdMin && totalPurchaseAmount <= rewardThresholdMax){
                    rewardPoints = totalPurchaseAmount.intValue();
                }else if(totalPurchaseAmount > rewardThresholdMax){
                    rewardPoints = ((totalPurchaseAmount.intValue() - rewardThresholdMax) * 2) + rewardThresholdMin;
                }
            }catch (Exception e){
                log.error("Unable to calculate Rewards :: "+e.fillInStackTrace());
                throw new UnHandeledEntityException("Unable to calculate Rewards :: "+e.getMessage());
            }

            return  rewardPoints;
        }
        return rewardPoints;
    }
}
