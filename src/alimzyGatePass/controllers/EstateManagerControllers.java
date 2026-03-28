package alimzyGatePass.controllers;

import alimzyGatePass.dtos.requests.OnboardResidentRequest;
import alimzyGatePass.dtos.responses.OnboardResidentResponse;
import alimzyGatePass.services.ResidentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estate-manager")
public class EstateManagerControllers {

    @Autowired
    private ResidentManagementService residentManagementService;

    @PostMapping("/onboard")
    public OnboardResidentResponse onboardNewResident(@RequestBody OnboardResidentRequest request) {
        return residentManagementService.onboardResident(request);
    }
}