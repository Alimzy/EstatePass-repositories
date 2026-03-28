package alimzyGatePass.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Visitor {

    @Id
    private String id;

    private String name;
    private String purposeOfVisit;
    private String phoneNumber;

}
