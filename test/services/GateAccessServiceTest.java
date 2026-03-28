package services;

import alimzyGatePass.data.models.Resident;
import alimzyGatePass.data.models.Types;
import alimzyGatePass.dtos.requests.GenerateResidentEntryCodeRequest;
import alimzyGatePass.dtos.requests.GenerateVisitorEntryCodeRequest;
import alimzyGatePass.dtos.requests.ValidateCodeRequest;
import alimzyGatePass.dtos.responses.GenerateResidentEntryCodeResponse;
import alimzyGatePass.dtos.responses.GenerateVisitorEntryCodeResponse;
import alimzyGatePass.dtos.responses.VallidateCodeResponse;
import alimzyGatePass.exceptions.InvalidGatePassException;
import alimzyGatePass.services.GateAccessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = alimzyGatePass.Main.class)
public class GateAccessServiceTest {
    @Autowired
    GateAccessService gateAccessService;

    @BeforeEach
    public void setUp() {
        gateAccessService.gatePassRepository.deleteAll();
    }

    @Test
    public void testInitialGatePassCountIsZero() {
        assertEquals(0, gateAccessService.gatePassRepository.count());
    }

    @Test
    public void testCanGenerateVisitorEntryCode() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setVisitorName("James Bond");
        request.setResidentId("1");
        request.setPurposeOfVisit("Family visit");
        request.setVisitorPhone("08012345678");

        gateAccessService.generateVisitorEntryCode(request);

        assertEquals(1, gateAccessService.gatePassRepository.count());
    }

    @Test
    public void testCanGenerateResidentEntryCode(){
        Resident resident = new Resident();
        resident.setName("John Doe");
        resident.setPhoneNumber("08012345678");
        resident.setHouseAddress("12 Lagos Street");

        gateAccessService.residentRepository.save(resident);

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setValidTill(LocalDateTime.now().plusHours(24));

        gateAccessService.generateResidentEntryCode(request);
        assertEquals(1, gateAccessService.gatePassRepository.count());

    }

    @Test
    void testCanGenerateExitCodeForResident() {

        Resident resident = new Resident();
        resident.setName("Alimzy");
        resident.setPhoneNumber("08056906817");
        resident.setHouseAddress("18 Awe Street");
        gateAccessService.residentRepository.save(resident);

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setValidTill(LocalDateTime.now().plusHours(24));
        GenerateResidentEntryCodeResponse entryResponse = gateAccessService.generateResidentEntryCode(request);


        gateAccessService.generateExitCode(entryResponse.getCode(), LocalDateTime.now().plusHours(2));

        assertEquals(1, gateAccessService.gatePassRepository.count());
    }

    @Test
    public void testGenerateExitCodeWithInvalidCode() {
        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.generateExitCode("INVALID", LocalDateTime.now().plusHours(2));
        });
    }

    @Test
    void testCanGenerateExitCodeForVisitor() {

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setVisitorName("James Bond");
        request.setResidentId("1");
        request.setPurposeOfVisit("Family visit");
        request.setVisitorPhone("08012345678");
        GenerateVisitorEntryCodeResponse entryResponse = gateAccessService.generateVisitorEntryCode(request);


        gateAccessService.generateExitCode(entryResponse.getCode(), LocalDateTime.now().plusHours(2));

        assertEquals(1, gateAccessService.gatePassRepository.count());
    }

    @Test
    public void testCanExtendTime() {

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setVisitorName("James Bond");
        request.setResidentId("1");
        request.setPurposeOfVisit("Family visit");
        request.setVisitorPhone("08012345678");
        GenerateVisitorEntryCodeResponse entryResponse = gateAccessService.generateVisitorEntryCode(request);


        String result = gateAccessService.extendTime(entryResponse.getCode(), LocalDateTime.now().plusHours(48));

        assertEquals("Time extended successfully", result);
    }

    @Test
    public void testExtendTimeWithInvalidCode() {
        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.extendTime("INVALID", LocalDateTime.now().plusHours(48));
        });
    }

    @Test
    void testCanDisableCode() {

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setVisitorName("Alimzy");
        request.setResidentId("1");
        request.setPurposeOfVisit("Family visit");
        request.setVisitorPhone("08056906817");
        GenerateVisitorEntryCodeResponse entryResponse = gateAccessService.generateVisitorEntryCode(request);

        String result = gateAccessService.disableCode(entryResponse.getCode());

        assertEquals("code disable successful", result);
    }

    @Test
    void testDisabledCodeCannotBeUsed() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setVisitorName("Alimzy");
        request.setResidentId("1");
        request.setPurposeOfVisit("Family visit");
        request.setVisitorPhone("08056906817");
        GenerateVisitorEntryCodeResponse entryResponse = gateAccessService.generateVisitorEntryCode(request);

        gateAccessService.disableCode(entryResponse.getCode());

        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.extendTime(entryResponse.getCode(), LocalDateTime.now().plusHours(48));
        });
    }

    @Test
    void testCanValidateCode() {

        Resident resident = new Resident();
        resident.setName("Alimzy");
        resident.setPhoneNumber("08056906817");
        resident.setHouseAddress("18 Awe Street");
        gateAccessService.residentRepository.save(resident);


        GenerateResidentEntryCodeRequest entryRequest = new GenerateResidentEntryCodeRequest();
        entryRequest.setResidentId(resident.getId());
        entryRequest.setValidTill(LocalDateTime.now().plusHours(24));
        GenerateResidentEntryCodeResponse entryResponse = gateAccessService.generateResidentEntryCode(entryRequest);


        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(entryResponse.getCode());
        validateRequest.setCodeType(Types.ENTRY.name());
        VallidateCodeResponse validateResponse = gateAccessService.validateCode(validateRequest);

        assertNotNull(validateResponse);
        assertEquals("Alimzy", validateResponse.getResidentName());
    }

    @Test
    void testValidateInvalidCode() {
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode("INVALID");
        request.setCodeType(Types.ENTRY.name());

        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.validateCode(request);
        });
    }

    @Test
    void testValidateDisabledCode() {

        Resident resident = new Resident();
        resident.setName("Alimzy");
        resident.setPhoneNumber("08056906817");
        resident.setHouseAddress("18 Awe Street");
        gateAccessService.residentRepository.save(resident);

        GenerateResidentEntryCodeRequest entryRequest = new GenerateResidentEntryCodeRequest();
        entryRequest.setResidentId(resident.getId());
        entryRequest.setValidTill(LocalDateTime.now().plusHours(24));
        GenerateResidentEntryCodeResponse entryResponse = gateAccessService.generateResidentEntryCode(entryRequest);

        gateAccessService.disableCode(entryResponse.getCode());

        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(entryResponse.getCode());
        validateRequest.setCodeType(Types.ENTRY.name());

        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.validateCode(validateRequest);
        });
    }

    @Test
    void testValidateExpiredCode() {

        Resident resident = new Resident();
        resident.setName("Alimzy");
        resident.setPhoneNumber("08056906817");
        resident.setHouseAddress("18 Awe Street");
        gateAccessService.residentRepository.save(resident);

        GenerateResidentEntryCodeRequest entryRequest = new GenerateResidentEntryCodeRequest();
        entryRequest.setResidentId(resident.getId());
        entryRequest.setValidTill(LocalDateTime.now().minusHours(1));
        GenerateResidentEntryCodeResponse entryResponse = gateAccessService.generateResidentEntryCode(entryRequest);

        ValidateCodeRequest validateRequest = new ValidateCodeRequest();
        validateRequest.setCode(entryResponse.getCode());
        validateRequest.setCodeType(Types.ENTRY.name());

        assertThrows(InvalidGatePassException.class, () -> {
            gateAccessService.validateCode(validateRequest);
        });
    }

}