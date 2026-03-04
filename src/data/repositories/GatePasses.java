package data.repositories;
import data.models.GatePass;
import java.util.ArrayList;
import java.util.List;

public class GatePasses implements GatePassRepo {
    private List<GatePass> gatePasses = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(gatePasses);
    }

    @Override
    public GatePass findById(int id) {
        for (GatePass pass : gatePasses) {
            if (pass.getId() == id) {
                return pass;
            }
        }
        return null;
    }

    @Override
    public GatePass save(GatePass pass) {
        if (pass.getId() == 0) {
            pass.setId(nextId++);
            gatePasses.add(pass);
        } else {
            GatePass existing = findById(pass.getId());
            if (existing != null) {
                int index = gatePasses.indexOf(existing);
                gatePasses.set(index, pass);
            } else {
                gatePasses.add(pass);
            }
        }
        return pass;
    }

    @Override
    public void delete(GatePass pass) {
        gatePasses.remove(pass);
    }

    @Override
    public void deleteById(int id) {
        GatePass pass = findById(id);
        if (pass != null) {
            gatePasses.remove(pass);
        }
    }

    @Override
    public void deleteByObject(GatePass pass) {
        delete(pass);
    }

    @Override
    public void deleteAll() {
        gatePasses.clear();
    }

    public int count() {
        return gatePasses.size();
    }
}
