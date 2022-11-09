package retailer.reward.system.controller;

import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.TransactionRequest;
import retailer.reward.system.repository.TransactionsEntity;
import retailer.reward.system.service.TransactionService;
import retailer.reward.system.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/saveTransaction")
    public ResponseEntity<Object> saveTransaction(@Validated @RequestBody TransactionRequest transactionRequest){
        ResponseDto<TransactionsEntity> transactionsEntity = transactionService.saveTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transactionsEntity);
    }

    @GetMapping( "/getTransactions")
    public ResponseEntity<Object> getTransactions(@RequestHeader(name = Constants.USER_NAME, required = true) String userName) throws Exception{
        ResponseDto<List<TransactionsEntity>> transactionsEntityList = transactionService.getTransactions(userName);
        return ResponseEntity.status(HttpStatus.OK).body(transactionsEntityList);
    }
}
