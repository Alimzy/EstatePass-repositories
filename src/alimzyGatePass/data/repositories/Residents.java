package alimzyGatePass.data.repositories;

import alimzyGatePass.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Residents extends MongoRepository<Resident, String> {

    Resident findByPhoneNumber(String phoneNumber);
}