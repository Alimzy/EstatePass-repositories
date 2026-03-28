package data.repositories;

import alimzyGatePass.data.models.Resident;
import alimzyGatePass.data.repositories.Residents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResidentsTest {

    Residents residents;
    Resident residentOne;
    Resident residentTwo;


    @BeforeEach
    public void setUp() {
        residents = new Residents();
        residentOne = new Resident();
        residentOne.setId("1");
        residentTwo = new Resident();
        residentTwo.setId("4");
    }


    @Test
    public void testThatResidentsDefaultSate_isZero() {
        int expected = 0;
        int actual = residents.count();
        assertEquals(expected, actual);

    }

    @Test
    public void testThatResidentCanBeSaved() {
        assertEquals(0, residents.count());
        residents.save(residentOne);
        assertEquals(1, residents.count());


    }

    @Test
    public void testThatMultipleResidentsCanBeSaved() {
        assertEquals(0, residents.count());
        residents.save(residentOne);
        residents.save(residentTwo);
        assertEquals(2, residents.count());


    }

    @Test
    public void testThatICanFindResidentsById() {
        assertEquals(0, residents.count());
        residents.save(residentOne);
        residents.save(residentTwo);
        assertEquals(residentTwo,residents.findById("4"));

    }

    @Test
    public void testThatICanDeleteResident(){
        assertEquals(0, residents.count());
        residents.save(residentOne);
        residents.save(residentTwo);
        residents.delete(residentTwo);
        assertEquals(1, residents.count());
    }

    @Test
    public void testThatICanDeleteResidentsById(){
        assertEquals(0, residents.count());
        residents.save(residentOne);
        residents.save(residentTwo);
        residents.delete(residentTwo);
        residents.deleteById("4");
        assertEquals(1, residents.count());
    }

    @Test
    public void testThatICanDeleteAllResidents(){
        assertEquals(0, residents.count());
        residents.save(residentOne);
        residents.save(residentTwo);
        residents.delete(residentTwo);
        residents.deleteAll();
        assertEquals(0, residents.count());
    }


}