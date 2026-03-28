package alimzyGatePass.dtos.responses;

import alimzyGatePass.data.models.Types;
import lombok.Data;

@Data
public class GenerateVisitorEntryCodeResponse {

    private String code;
    private String visitorName;
    private String validTill;
    private Types codeType;


}
