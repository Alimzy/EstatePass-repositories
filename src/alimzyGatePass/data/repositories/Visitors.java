package alimzyGatePass.data.repositories;
import alimzyGatePass.data.models.Visitor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Visitors implements VisitorRepo {


    private List<Visitor> visitors = new ArrayList<>();

    private int nextId = 1;

    @Override
    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    @Override
    public Visitor findById(String id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId().equals( id)) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public void save(Visitor visitor) {
        if (visitor.getId().equals("0")) {
            visitor.setId(nextId+ "1");
            visitors.add(visitor);
        } else {
            Visitor existing = findById(visitor.getId());
            if (existing != null) {
                int index = visitors.indexOf(existing);
                visitors.set(index, visitor);
            } else {
                visitors.add(visitor);
            }
        }
    }

    @Override
    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }

    @Override
    public void deleteById(String id) {
        Visitor visitor = findById(id);
        if (visitor != null) {
            visitors.remove(visitor);
        }
    }

    @Override
    public void deleteByObject(Visitor visitor) {
        delete(visitor);
    }

    @Override
    public void deleteAll() {
        visitors.clear();
    }

    public int count() {
        return visitors.size();
    }
}