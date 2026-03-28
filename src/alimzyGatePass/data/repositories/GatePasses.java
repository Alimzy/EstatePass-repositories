package alimzyGatePass.data.repositories;

import alimzyGatePass.data.models.GatePass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatePasses extends MongoRepository<GatePass, String> {

    GatePass findByCode(String code);
}



