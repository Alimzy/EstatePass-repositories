package alimzyGatePass.dtos.responses;

import alimzyGatePass.data.models.Types;
import lombok.Data;


@Data
public class GenerateResidentEntryCodeResponse {
    private String code;
    private String residentName;
    private Types codeType;
    private String validTill;
    private  String destination;


}
