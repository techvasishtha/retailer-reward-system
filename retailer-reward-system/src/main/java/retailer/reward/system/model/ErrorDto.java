package retailer.reward.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private Integer code;
    private String message;

    public ErrorDto(String message){
        this.message=message;
    }
}
