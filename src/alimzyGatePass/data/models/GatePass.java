
package alimzyGatePass.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "gate_passes")
public class GatePass {

    @Id
    private  String id;

    private String residentId;
    private String code;
    private Types passType;
    private Visitor visitor;
    private LocalDateTime dateCreated =  LocalDateTime.now();
    private LocalDateTime validTill;
    private boolean isValid = true;


}