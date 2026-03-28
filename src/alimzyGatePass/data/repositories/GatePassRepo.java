package alimzyGatePass.data.repositories;

import alimzyGatePass.data.models.GatePass;
import java.util.List;

public interface GatePassRepo {
    List<GatePass> findAll();
    GatePass findById(String id);
    GatePass save(GatePass pass);
    void delete(GatePass pass);
    void deleteById(String id);
    void deleteByObject(GatePass pass);
    void deleteAll();
    GatePass findByCode(String code);
    int count();
}