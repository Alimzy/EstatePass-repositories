package services;

import alimzyGatePass.dtos.requests.OnboardResidentRequest;
import alimzyGatePass.exceptions.ResidentAlreadyExistsException;
import alimzyGatePass.exceptions.ResidentDoNotExistException;
import alimzyGatePass.services.ResidentManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = alimzyGatePass.Main.class)
public class ResidentManagementServiceTest {
    @Autowired
    public ResidentManagementService residentManagementService;

    @BeforeEach
    public void setUp() {
        residentManagementService.residentRepository.deleteAll();
    }

    @Test
   public void testInitialResidentCountIsZero() {
        assertEquals(0, residentManagementService.residentRepository.count());
    }

    @Test
    public void testCanOnboardAResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        assertEquals(1, residentManagementService.residentRepository.count());
    }

    @Test
    public void testCanOnboardMultipleResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        OnboardResidentRequest requestTwo = new OnboardResidentRequest();
        requestTwo.setName("Dayo");
        requestTwo.setPhone("07014922698");
        requestTwo.setAddress("18 Bale Street");
        requestTwo.setEmail("dayo@gmail.com");

        residentManagementService.onboardResident(requestTwo);

        OnboardResidentRequest requestThree = new OnboardResidentRequest();
        requestThree.setName("Fawaz");
        requestThree.setPhone("08034337420");
        requestThree.setAddress("18 Coker Street");
        requestThree.setEmail("fawaz@gmail.com");

        residentManagementService.onboardResident(requestThree);

        assertEquals(3, residentManagementService.residentRepository.count());
    }

    @Test
    public void testCannotOnboardDuolicateResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        OnboardResidentRequest requestTwo = new OnboardResidentRequest();
        requestTwo.setName("Dayo");
        requestTwo.setPhone("07014922698");
        requestTwo.setAddress("18 Bale Street");
        requestTwo.setEmail("dayo@gmail.com");

        residentManagementService.onboardResident(requestTwo);

        assertThrows(ResidentAlreadyExistsException.class, () -> {
            residentManagementService.onboardResident(request);
        });
    }

    @Test
    public void testCanDeleteResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        OnboardResidentRequest requestTwo = new OnboardResidentRequest();
        requestTwo.setName("Dayo");
        requestTwo.setPhone("07014922698");
        requestTwo.setAddress("18 Bale Street");
        requestTwo.setEmail("dayo@gmail.com");

        residentManagementService.onboardResident(requestTwo);

        OnboardResidentRequest requestThree = new OnboardResidentRequest();
        requestThree.setName("Fawaz");
        requestThree.setPhone("08034337420");
        requestThree.setAddress("18 Coker Street");
        requestThree.setEmail("fawaz@gmail.com");

        residentManagementService.onboardResident(requestThree);

        assertEquals(3, residentManagementService.residentRepository.count());
        residentManagementService.deleteResident("08056906817");
        assertEquals(2, residentManagementService.residentRepository.count());
    }

    @Test
    public void testCanDisableResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        String result = residentManagementService.disableResident("08056906817");
        assertEquals("Resident disabled successfully", result);
    }

    @Test
    public void testDisableResidentThatDoesNotExist() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Alimzy");
        request.setPhone("08056906817");
        request.setAddress("18 Awe Street");
        request.setEmail("alimzy@gmail.com");

        residentManagementService.onboardResident(request);

        assertThrows(ResidentDoNotExistException.class, () -> {
            residentManagementService.disableResident("08012345678");
        });
    }


}