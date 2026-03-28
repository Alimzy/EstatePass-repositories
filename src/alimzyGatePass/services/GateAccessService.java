package alimzyGatePass.services;

import alimzyGatePass.data.models.GatePass;
import alimzyGatePass.data.models.Resident;
import alimzyGatePass.data.models.Types;
import alimzyGatePass.data.repositories.GatePasses;
import alimzyGatePass.data.repositories.Residents;
import alimzyGatePass.dtos.requests.GenerateResidentEntryCodeRequest;
import alimzyGatePass.dtos.requests.GenerateVisitorEntryCodeRequest;
import alimzyGatePass.dtos.requests.ValidateCodeRequest;
import alimzyGatePass.dtos.responses.GenerateResidentEntryCodeResponse;
import alimzyGatePass.dtos.responses.GenerateVisitorEntryCodeResponse;
import alimzyGatePass.dtos.responses.VallidateCodeResponse;
import alimzyGatePass.exceptions.InvalidGatePassException;
import alimzyGatePass.exceptions.ResidentDoNotExistException;
import alimzyGatePass.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class GateAccessService {

    @Autowired
   public Residents residentRepository;

    @Autowired
    public GatePasses gatePassRepository;

    @Autowired
    public Mapper mapper;

    public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(GenerateVisitorEntryCodeRequest generateVisitorEntryCodeRequest) {



            String code = generateCode();
            GatePass gatePass = mapper.map(generateVisitorEntryCodeRequest, code);
            gatePassRepository.save(gatePass);
            return mapper.map(gatePass, generateVisitorEntryCodeRequest.getVisitorName());

    }


    public GenerateResidentEntryCodeResponse generateResidentEntryCode(GenerateResidentEntryCodeRequest generateResidentEntryCodeRequest) {


        Resident resident = residentRepository.findById(generateResidentEntryCodeRequest.getResidentId()).orElseThrow(() -> new ResidentDoNotExistException("Resident not found"));
        String code = generateCode();
        GatePass gatePass = mapper.map(generateResidentEntryCodeRequest, code, resident.getId());
        gatePassRepository.save(gatePass);
        return mapper.mapToResidentEntryCodeResponse(gatePass, resident.getName());


    }


    public String disableCode(String code) {

        for(GatePass gatePass : gatePassRepository.findAll()){
            if(gatePass.getCode().equals(code)){
               gatePass.setValid(false) ;
                gatePassRepository.save(gatePass);
            }
        }
        return "code disable successful";
    }




    public String generateExitCode(String entryCode, LocalDateTime validTill) {

        GatePass gatePass = gatePassRepository.findByCode(entryCode);


        if (gatePass == null) {
            throw new  InvalidGatePassException("Entry code not found");
        }

        String exitCode = generateCode();


        gatePass.setCode(exitCode);
        gatePass.setValidTill(validTill);
        gatePass.setPassType(Types.EXIT);
        gatePassRepository.save(gatePass);

        return exitCode;
    }




    public String extendTime(String entryCode, LocalDateTime time) {
        GatePass gatePass = gatePassRepository.findByCode(entryCode);

        if (gatePass == null || !gatePass.isValid()) {
            throw new InvalidGatePassException("Gate pass not found or no longer valid");
        }

        gatePass.setValidTill(time);
        gatePassRepository.save(gatePass);

        return "Time extended successfully";

    }




    public VallidateCodeResponse validateCode(ValidateCodeRequest validateCodeRequest) {
        GatePass gatePass = gatePassRepository.findByCode(validateCodeRequest.getCode());

        if (gatePass == null) {
            throw new InvalidGatePassException("Code not found");
        }

        if (!gatePass.isValid()) {
            throw new InvalidGatePassException("Code is no longer valid");
        }


        if (gatePass.getValidTill().isBefore(LocalDateTime.now())) {
            gatePass.setValid(false);
            gatePassRepository.save(gatePass);
            throw new InvalidGatePassException("Code has expired");
        }


        Resident resident = residentRepository.findById(gatePass.getResidentId()).orElseThrow(() -> new ResidentDoNotExistException("Resident not found"));

        VallidateCodeResponse response = new VallidateCodeResponse();
        response.setResidentName(resident.getName());
        response.setCodeType(validateCodeRequest.getCodeType());
        response.setCreatedBy(resident.getName());

        if (gatePass.getVisitor() != null) {
            response.setVisitorsName(gatePass.getVisitor().getName());
        }

        return response;
    }


    public String generateCode() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";
        Random random = new Random();

        for (int count = 0; count < length; count++) {
            int index = random.nextInt(characters.length());
            code = code + characters.charAt(index);
        }

        return code;
    }



}
