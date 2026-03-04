package data.repositories;
import data.models.Resident;
import java.util.List;

public interface ResidentRepo {
    List<Resident> findAll();
    Resident findById(int id);
    Resident save(Resident resident);
    void delete(Resident resident);
    void deleteById(int id);
    void deleteByObject(Resident resident);
    void deleteAll();
}