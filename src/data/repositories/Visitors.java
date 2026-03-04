package data.repositories;
import data.models.Visitor;
import java.util.ArrayList;
import java.util.List;

public class Visitors implements VisitorRepo {
    private List<Visitor> visitors = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    @Override
    public Visitor findById(int id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId() == id) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public void save(Visitor visitor) {
        if (visitor.getId() == 0) {
            visitor.setId(nextId++);
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
    public void deleteById(int id) {
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