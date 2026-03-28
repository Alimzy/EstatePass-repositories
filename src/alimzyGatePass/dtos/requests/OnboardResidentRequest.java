package alimzyGatePass.dtos.requests;

import lombok.Data;

@Data
public class OnboardResidentRequest {
    private String phone;
    private String email;
    private String name;
    private String address;


}
