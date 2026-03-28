package alimzyGatePass.controllers;

import alimzyGatePass.dtos.requests.GenerateResidentEntryCodeRequest;
import alimzyGatePass.dtos.responses.GenerateResidentEntryCodeResponse;
import alimzyGatePass.services.GateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resident")
public class ResidentControllers {

    @Autowired
    private GateAccessService gateAccessService;

    @PostMapping("/generate-entry-code")
    public GenerateResidentEntryCodeResponse generateResidentEntryCode(@RequestBody GenerateResidentEntryCodeRequest request) {
        return gateAccessService.generateResidentEntryCode(request);
    }
}