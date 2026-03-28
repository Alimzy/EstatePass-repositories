package data.repositories;

import alimzyGatePass.data.models.Visitor;
import alimzyGatePass.data.repositories.Visitors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsTest {

    Visitors visitors;
    Visitor visitorOne;
    Visitor visitorTwo;


    @BeforeEach
    public void setUp() {
        visitors = new Visitors();
        visitorOne = new Visitor();
        visitorOne.setId("1");
        visitorTwo = new Visitor();
        visitorTwo.setId("4");
    }


    @Test
    public void testThatVisitorsDefaultSate_isZero() {
        int expected = 0;
        int actual = visitors.count();
        assertEquals(expected, actual);

    }

    @Test
    public void testThatVisitorsCanBeSaved() {
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        assertEquals(1, visitors.count());


    }

    @Test
    public void testThatMultipleVisitorsCanBeSaved() {
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        visitors.save(visitorTwo);
        assertEquals(2, visitors.count());


    }

    @Test
    public void testThatICanFindVisitorsById() {
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        visitors.save(visitorTwo);
        assertEquals(visitorTwo,visitors.findById("4"));

    }

    @Test
    public void testThatICanDeleteVisitors(){
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        visitors.save(visitorTwo);
        visitors.delete(visitorTwo);
        assertEquals(1, visitors.count());
    }

    @Test
    public void testThatICanDeleteVisitorById(){
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        visitors.save(visitorTwo);
        visitors.delete(visitorTwo);
        visitors.deleteById("4");
        assertEquals(1, visitors.count());
    }

    @Test
    public void testThatICanDeleteAllVisitors(){
        assertEquals(0, visitors.count());
        visitors.save(visitorOne);
        visitors.save(visitorTwo);
        visitors.delete(visitorOne);
        visitors.deleteAll();
        assertEquals(0, visitors.count());
    }


}