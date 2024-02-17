/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
        setC = BoundedSetOfNaturals.fromArray(new int[] { 50, 60 });
    }

    @AfterEach
    void tearDown() {
        setA = setB = setC = null;
    }

    @DisplayName("Add element to set")
    @Test
    void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
    }

    @DisplayName("Set refuseses invalid inputs using FromArray method")
    @Test
    void testAddFromBadArray() {
        int[] elems = new int[] { 10, -20, -30 };

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @DisplayName("Set refuses duplicate inputs")
    @Test
    void testAddDuplicateInput() {
        int num1 = 10;
        setA.add(num1);
        assertThrows(IllegalArgumentException.class, () -> {
            setA.add(num1);
        }, "add: element was added when it was already in set.");
    }

    @DisplayName("Set refuses illegal number")
    @Test
    void testAddIllegalNumber() {
        int bad_num = -20;
        assertThrows(IllegalArgumentException.class, () -> {
            setA.add(bad_num);
        }, "add: element was added when it is a illegal number.");
    }

    @DisplayName("Set refuses addition when at max size")
    @Test
    void testAddWhenAtMaxSize() {
        int num1 = 10;
        int num2 = 20;
        int num3 = 30;
        setA.add(num1);
        setA.add(num2);
        assertThrows(IllegalArgumentException.class, () -> {
            setA.add(num3);
        }, "add: element was added when set was at max size.");
    }

    @DisplayName("Set is not equal to null")
    @Test
    void testEqualsNull() {
        assertFalse(setA.equals(null), "equals: setA is not null");
    }

    @DisplayName("Set is not equal to a different type")
    @Test
    void testEqualsDifferentType() {
        assertFalse(setA.equals(2), "equals: setA != 2");
    }

    @DisplayName("Set is equal to itself")
    @Test
    void testEqualsSelf() {
        assertEquals(setA, setA, "equals: setA must equal setA");
    }

    @DisplayName("Hashcode must be the same when calculated for the same object several times")
    @Test
    void testHashCodeConsistency() {
        int hashCode = setA.hashCode();

        for (int i = 0; i < 10; i++) {
            assertEquals(hashCode, setA.hashCode());
        }
    }

    @DisplayName("Hashcode must be the same for two objects who are equal")
    @Test
    void testHashCodeEquality() {
        BoundedSetOfNaturals set = new BoundedSetOfNaturals(5);
        BoundedSetOfNaturals anotherset = new BoundedSetOfNaturals(5);

        assertEquals(set, anotherset);

        assertEquals(set.hashCode(), anotherset.hashCode());
    }

    @DisplayName("Hashcode must be different for two objects who are not equal")
    @Test
    void testHashCodeInequality() {
        assertNotEquals(setA, setB);
        assertNotEquals(setB, setC);

        assertNotEquals(setA.hashCode(), setB.hashCode());
        assertNotEquals(setB.hashCode(), setC.hashCode());
    }

    @DisplayName("Intersect returns true if a subset intersects with the main set")
    @Test
    void testIntersect() {
        BoundedSetOfNaturals setD = BoundedSetOfNaturals.fromArray(new int[] { 50, 60, 70 });

        assertTrue(setB.intersects(setC));
        assertTrue(setC.intersects(setB));

        assertFalse(setB.intersects(setD));
        assertFalse(setD.intersects(setB));
    }
}
