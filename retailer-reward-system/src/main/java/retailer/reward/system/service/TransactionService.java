package retailer.reward.system.service;

import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.TransactionRequest;
import retailer.reward.system.repository.TransactionsEntity;

import java.util.List;

public interface TransactionService {

   ResponseDto<TransactionsEntity> saveTransaction(TransactionRequest transactionRequest) throws Error;

   ResponseDto<List<TransactionsEntity>> getTransactions(String userName);

   Long findUserId(String userName);


}
