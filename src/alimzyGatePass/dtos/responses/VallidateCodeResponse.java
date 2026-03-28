package alimzyGatePass.dtos.responses;

import lombok.Data;

@Data
public class VallidateCodeResponse {

    private String residentName;
    private String visitorsName;
    private String codeType;
    private String createdBy;



}
