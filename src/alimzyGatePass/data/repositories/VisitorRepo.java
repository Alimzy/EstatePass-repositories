package alimzyGatePass.data.repositories;
import alimzyGatePass.data.models.Visitor;
import java.util.List;

public interface VisitorRepo {
    List<Visitor> findAll();
    Visitor findById(String Id);
    void save(Visitor visitor);
    void delete(Visitor visitor);
    void deleteById(String Id);
    void deleteByObject(Visitor visitor);
    void deleteAll();
}