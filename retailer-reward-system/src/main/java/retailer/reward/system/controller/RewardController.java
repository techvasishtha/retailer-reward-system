package retailer.reward.system.controller;

import retailer.reward.system.model.ResponseDto;
import retailer.reward.system.model.Rewards;
import retailer.reward.system.service.RewardService;
import retailer.reward.system.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping( "/getRewards")
    public ResponseEntity<Object> getRewards(@NotNull @RequestHeader(name = Constants.USER_NAME) String userName){
        ResponseDto<Rewards> totalRewardForUserPerDay = rewardService.getRewards(userName);
        return ResponseEntity.status(HttpStatus.OK).body(totalRewardForUserPerDay);
    }

    @GetMapping( "/getRewardsByMonthly")
    public ResponseEntity<Object> getRewardsByMonthly(@NotNull @RequestHeader(name = Constants.USER_NAME) String userName){
        ResponseDto<Rewards> totalRewardForUserPerDay = rewardService.getRewardsByMonthly(userName);
        return ResponseEntity.status(HttpStatus.OK).body(totalRewardForUserPerDay);
    }

    @GetMapping( "/getRewardsByQuarterly")
    public ResponseEntity<Object> getRewardsByQuarterly(@NotNull @RequestHeader(name = Constants.USER_NAME) String userName){
        ResponseDto<Rewards> totalRewardForUserPerDay = rewardService.getRewardsByQuarterly(userName);
        return ResponseEntity.status(HttpStatus.OK).body(totalRewardForUserPerDay);
    }
}
