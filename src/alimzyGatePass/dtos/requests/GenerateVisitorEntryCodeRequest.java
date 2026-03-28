package alimzyGatePass.dtos.requests;

import lombok.Data;

@Data
public class GenerateVisitorEntryCodeRequest {
    private String residentId;
    private String visitorPhone;
    private String purposeOfVisit;
    private String visitorName;


}
