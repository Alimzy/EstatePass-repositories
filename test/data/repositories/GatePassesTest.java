package data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import data.repositories.GatePasses;
import data.models.GatePass;

import static org.junit.jupiter.api.Assertions.*;

class GatePassesTest {
    GatePasses gatePasses;
    GatePass myPass;
    GatePass myPassTwo;


    @BeforeEach
    public void setUp() {
        gatePasses = new GatePasses();
        myPass = new GatePass();
        myPass.setResidentId(1);
        myPassTwo = new GatePass();
    }


    @Test
    public void testThatMyGatePassesDefaultSate_isZero() {
        int expected = 0;
        int actual = gatePasses.count();
        assertEquals(expected, actual);

    }

    @Test
    public void testThatGatePassCanBeSaved() {
        assertEquals(0, gatePasses.count());
        gatePasses.save(myPass);
        assertEquals(1, gatePasses.count());


    }

    @Test
    public void testThatMultipleGatePassesCanBeSaved() {
        assertEquals(0, gatePasses.count());
        gatePasses.save(myPass);
        gatePasses.save(myPassTwo);
        assertEquals(2, gatePasses.count());


    }

    @Test
    public void testThatICanFindPassById() {
        assertEquals(0, gatePasses.count());
        gatePasses.save(myPass);
        gatePasses.save(myPassTwo);
        GatePass getById = gatePasses.findById(1);
        assertEquals(1,getById.getResidentId());

    }


}
