package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GardenTest {

    static Garden testGarden1;
    static Garden testGarden2;
    static Garden testGarden3;

    @BeforeEach
    void setup() {
        testGarden1 = new Garden();
        testGarden2 = new Garden();
        testGarden3 = new Garden();
    }

    @Test
    void getGardenId() {
        Integer expected1 = testGarden1.getGardenId();
        Integer expected2 = testGarden2.getGardenId();
        Integer expected3 = testGarden3.getGardenId();

        Integer actual1 = testGarden1.getGardenId();
        Integer actual2 = testGarden2.getGardenId();
        Integer actual3 = testGarden3.getGardenId();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    void setGardenId() {
        Integer expected1 = 5;
        Integer expected2 = 41;
        Integer expected3 = 123;

        testGarden1.setGardenId(expected1);
        testGarden2.setGardenId(expected2);
        testGarden3.setGardenId(expected3);

        assertEquals(expected1, testGarden1.getGardenId());
        assertEquals(expected2, testGarden2.getGardenId());
        assertEquals(expected3, testGarden3.getGardenId());
    }

}