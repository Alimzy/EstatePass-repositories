package alimzyGatePass.data.repositories;
import alimzyGatePass.data.models.Resident;
import java.util.List;

public interface ResidentRepo {
    List<Resident> findAll();
    Resident findById(String id);
    Resident save(Resident resident);
    void delete(Resident resident);
    void deleteById(String id);
    void deleteByObject(Resident resident);
    void deleteAll();

    Resident findByPhoneNumber(String phoneNumber);

    int count();
}