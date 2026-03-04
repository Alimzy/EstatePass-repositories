package data.repositories;
import data.models.Visitor;
import java.util.List;

public interface VisitorRepo {
    List<Visitor> findAll();
    Visitor findById(int Id);
    void save(Visitor visitor);
    void delete(Visitor visitor);
    void deleteById(int Id);
    void deleteByObject(Visitor visitor);
    void deleteAll();
}