package retailer.reward.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Rewards implements Serializable {

    private String rewardFrequency;
    private Long rewardPoints;
    private String userName;

}
