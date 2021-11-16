package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatchTest {

    Patch testPatch1;
    Patch testPatch2;
    Patch testPatch3;
    Garden garden1;
    Garden garden2;
    Garden garden3;

    @BeforeEach
    void setUp() {
        testPatch1 = new Patch();
        testPatch2 = new Patch();
        testPatch3 = new Patch();
        garden1 = new Garden();
        garden2 = new Garden();
        garden3 = new Garden();
        garden1.setGardenId(1);
        garden2.setGardenId(2);
        garden3.setGardenId(3);
    }

    @Test
    void getPatchId() {
        testPatch1.setPatchId(5);
        testPatch2.setPatchId(373);
        testPatch3.setPatchId(647);
        assertEquals(5, testPatch1.getPatchId());
        assertEquals(373, testPatch2.getPatchId());
        assertEquals(647, testPatch3.getPatchId());
    }

    @Test
    void setPatchId() {
        int test1 = 526;
        int test2 = 268;
        int test3 = 47;
        int test4 = test1 + 50;
        int test5 = test2 + 40;
        int test6 = test3 + 30;
        testPatch1.setPatchId(test4);
        testPatch2.setPatchId(test5);
        testPatch3.setPatchId(test6);
        assertEquals(test1 + 50, testPatch1.getPatchId());
        assertEquals(test2 + 40, testPatch2.getPatchId());
        assertEquals(test3 + 30, testPatch3.getPatchId());
    }

    @Test
    void getGarden() {
        testPatch1.setGarden(garden1);
        testPatch2.setGarden(garden2);
        testPatch3.setGarden(garden3);
        assertEquals(1, testPatch1.getGarden().getGardenId());
        assertEquals(2, testPatch2.getGarden().getGardenId());
        assertEquals(3, testPatch3.getGarden().getGardenId());
    }

    @Test
    void setGarden() {
        testPatch1.setGarden(garden1);
        testPatch2.setGarden(garden2);
        testPatch3.setGarden(garden3);
        testPatch1.setGarden(testPatch2.getGarden());
        testPatch3.setGarden(testPatch2.getGarden());
        assertEquals(2, testPatch1.getGarden().getGardenId());
        assertEquals(2, testPatch2.getGarden().getGardenId());
        assertEquals(2, testPatch3.getGarden().getGardenId());
    }

    @Test
    void getCrop() {
        testPatch1.setCrop("turnips");
        testPatch2.setCrop("tomatoes");
        testPatch3.setCrop("pumpkins");
        assertEquals("turnips", testPatch1.getCrop());
        assertEquals("tomatoes", testPatch2.getCrop());
        assertEquals("pumpkins", testPatch3.getCrop());
    }

    @Test
    void setCrop() {
        testPatch1.setCrop("turnips");
        testPatch2.setCrop("tomatoes");
        testPatch3.setCrop("pumpkins");
        testPatch1.setCrop(testPatch2.getCrop());
        testPatch3.setCrop(testPatch2.getCrop());
        assertEquals("tomatoes", testPatch1.getCrop());
        assertEquals("tomatoes", testPatch2.getCrop());
        assertEquals("tomatoes", testPatch3.getCrop());
    }
}