package alimzyGatePass.services;

import alimzyGatePass.data.models.Resident;
import alimzyGatePass.data.repositories.Residents;
import alimzyGatePass.dtos.requests.OnboardResidentRequest;
import alimzyGatePass.dtos.responses.OnboardResidentResponse;
import alimzyGatePass.exceptions.ResidentAlreadyExistsException;
import alimzyGatePass.exceptions.ResidentDoNotExistException;
import alimzyGatePass.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentManagementService  {

    @Autowired
    public Residents residentRepository;
    @Autowired
    private Mapper mapper;

    public OnboardResidentResponse onboardResident(OnboardResidentRequest onboardResidentRequest) {

        Resident resident = mapper.map(onboardResidentRequest);
        validateCheckDuplicate(resident);
        residentRepository.save(resident);
        return mapper.map(resident);


    }


    public List<String> viewResident() {

        List<String> residentNames = new ArrayList<>();
        for (Resident resident : residentRepository.findAll()) {
            residentNames.add(resident.getName());
        }
        return residentNames;
    }


    public void deleteResident(String phoneNumber) {
        Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null) {
            throw new ResidentDoNotExistException("Resident not found");
        }

        residentRepository.delete(resident);
    }


    public void validateCheckDuplicate(Resident resident) {
        Resident existingResident = residentRepository.findByPhoneNumber(resident.getPhoneNumber());
        if (existingResident != null) {
            throw new ResidentAlreadyExistsException("Resident already exists");
        }
    }


    public String disableResident(String phoneNumber) {
        Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null) {
            throw new ResidentDoNotExistException("Resident not found");
        }

        resident.setEnabled(false);
        residentRepository.save(resident);

        return "Resident disabled successfully";
    }




}
