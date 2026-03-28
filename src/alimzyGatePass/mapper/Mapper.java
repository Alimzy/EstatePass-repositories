package alimzyGatePass.mapper;

import alimzyGatePass.data.models.GatePass;
import alimzyGatePass.data.models.Resident;
import alimzyGatePass.data.models.Types;
import alimzyGatePass.dtos.requests.GenerateResidentEntryCodeRequest;
import alimzyGatePass.dtos.requests.GenerateVisitorEntryCodeRequest;
import alimzyGatePass.dtos.requests.OnboardResidentRequest;
import alimzyGatePass.dtos.responses.GenerateResidentEntryCodeResponse;
import alimzyGatePass.dtos.responses.GenerateVisitorEntryCodeResponse;
import alimzyGatePass.dtos.responses.OnboardResidentResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {

    public Resident map(OnboardResidentRequest request) {
        Resident resident = new Resident();
        resident.setName(request.getName());
        resident.setPhoneNumber(request.getPhone());
        resident.setHouseAddress(request.getAddress());
        resident.setEmail(request.getEmail());
        return resident;
    }

    public OnboardResidentResponse map(Resident resident) {
        OnboardResidentResponse response = new OnboardResidentResponse();
        response.setResidentId(resident.getId());
        response.setResidentName(resident.getName());
        response.setDateRegistered(resident.getDateRegistered().toString());
        return response;
    }

    public GatePass map(GenerateVisitorEntryCodeRequest request, String code) {
        GatePass gatePass = new GatePass();
        gatePass.setCode(code);
        gatePass.setPassType(Types.ENTRY);
        gatePass.setValidTill(LocalDateTime.now().plusHours(24));
        return gatePass;
    }

    public GenerateVisitorEntryCodeResponse map(GatePass gatePass, String visitorName) {
        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
        response.setCode(gatePass.getCode());
        response.setVisitorName(visitorName);
        response.setCodeType(Types.ENTRY);
        response.setValidTill(gatePass.getValidTill().toString());
        return response;
    }

    public GatePass map(GenerateResidentEntryCodeRequest request, String code, String residentId) {
        GatePass gatePass = new GatePass();
        gatePass.setCode(code);
        gatePass.setPassType(Types.ENTRY);
        gatePass.setValidTill(request.getValidTill());
        gatePass.setResidentId(residentId);
        return gatePass;
    }

    public GenerateResidentEntryCodeResponse mapToResidentEntryCodeResponse(GatePass gatePass, String residentName) {
        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();
        response.setCode(gatePass.getCode());
        response.setResidentName(residentName);
        response.setCodeType(Types.ENTRY);
        response.setValidTill(gatePass.getValidTill().toString());
        return response;
    }
}
