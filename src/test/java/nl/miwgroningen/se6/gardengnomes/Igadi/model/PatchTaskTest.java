package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatchTaskTest {

    Patch testPatch1;
    Patch testPatch2;
    Patch testPatch3;
    Garden testGarden1;
    Garden testGarden2;
    Garden testGarden3;
    PatchTask testPatchTask1;
    PatchTask testPatchTask2;
    PatchTask testPatchTask3;

    @BeforeEach
    void setup() {
        testPatch1 = new Patch();
        testPatch2 = new Patch();
        testPatch3 = new Patch();
        testGarden1 = new Garden();
        testGarden2 = new Garden();
        testGarden3 = new Garden();
        testPatchTask1 = new PatchTask();
        testPatchTask2 = new PatchTask();
        testPatchTask3 = new PatchTask();
        testPatchTask1.setTaskId(1);
        testPatchTask2.setTaskId(2);
        testPatchTask3.setTaskId(3);
        testGarden1.setGardenId(1);
        testGarden2.setGardenId(2);
        testGarden3.setGardenId(3);
    }


    @Test
    void getPatchTaskIsDone(){
        testPatchTask1.setDone(true);
        testPatchTask2.setDone(false);
        testPatchTask3.setDone(true);
        assertTrue(testPatchTask1.getIsDone());
        assertFalse(testPatchTask2.getIsDone());
        assertTrue(testPatchTask3.getIsDone());
    }

    @Test
    void setPatchTaskIsDone(){
        testPatchTask1.setDone(true);
        testPatchTask2.setDone(false);
        testPatchTask3.setDone(true);
        testPatchTask1.setDone(testPatchTask2.getIsDone());
        testPatchTask3.setDone(testPatchTask2.getIsDone());
        assertFalse(testPatchTask1.getIsDone());
        assertFalse(testPatchTask2.getIsDone());
        assertFalse(testPatchTask3.getIsDone());
    }

    @Test
    void getPatchTaskDescription(){
        testPatchTask1.setTaskDescription("Put the water on the potatoes");
        testPatchTask2.setTaskDescription("Please plant the strawberries soon");
        testPatchTask3.setTaskDescription("Remove the weed from the potatoes");
        assertEquals("Put the water on the potatoes", testPatchTask1.getTaskDescription());
        assertEquals("Please plant the strawberries soon", testPatchTask2.getTaskDescription());
        assertEquals("Remove the weed from the potatoes", testPatchTask3.getTaskDescription());
    }

    @Test
    void setPatchTaskDescription() {
        testPatchTask1.setTaskDescription("Put the water on the potatoes");
        testPatchTask2.setTaskDescription("Please plant the strawberries soon");
        testPatchTask3.setTaskDescription("Remove the weed from the potatoes");
        testPatchTask2.setTaskDescription(testPatchTask1.getTaskDescription());
        testPatchTask3.setTaskDescription(testPatchTask1.getTaskDescription());
        assertEquals("Put the water on the potatoes", testPatchTask1.getTaskDescription());
        assertEquals("Put the water on the potatoes", testPatchTask2.getTaskDescription());
        assertEquals("Put the water on the potatoes", testPatchTask3.getTaskDescription());
    }


    @Test
    void getPatchTaskId() {
        testPatchTask1.setTaskId(2);
        testPatchTask2.setTaskId(3);
        testPatchTask3.setTaskId(4);
        assertEquals(2, testPatchTask1.getTaskId());
        assertEquals(3, testPatchTask2.getTaskId());
        assertEquals(4, testPatchTask3.getTaskId());
    }

    @Test
    void setPatchTaskId() {
        testPatchTask1.setTaskId(2);
        testPatchTask2.setTaskId(3);
        testPatchTask3.setTaskId(4);
        testPatchTask2.setTaskId(testPatchTask1.getTaskId());
        testPatchTask3.setTaskId(testPatchTask1.getTaskId());
        assertEquals(2, testPatchTask1.getTaskId());
        assertEquals(2, testPatchTask2.getTaskId());
        assertEquals(2, testPatchTask3.getTaskId());
    }

    @Test
    void getPatchTaskName() {
        testPatchTask1.setTaskName("Plant the potatoes");
        testPatchTask2.setTaskName("Water the potatoes");
        testPatchTask3.setTaskName("Fertilize the potatoes");
        assertEquals("Plant the potatoes", testPatchTask1.getTaskName());
        assertEquals("Water the potatoes", testPatchTask2.getTaskName());
        assertEquals("Fertilize the potatoes", testPatchTask3.getTaskName());
    }


    @Test
    void setPatchTaskName() {
        testPatchTask1.setTaskName("Plant the potatoes");
        testPatchTask2.setTaskName("Water the potatoes");
        testPatchTask3.setTaskName("Fertilize the potatoes");
        testPatchTask1.setTaskName(testPatchTask2.getTaskName());
        testPatchTask3.setTaskName(testPatchTask2.getTaskName());
        assertEquals("Water the potatoes", testPatchTask1.getTaskName());
        assertEquals("Water the potatoes", testPatchTask2.getTaskName());
        assertEquals("Water the potatoes", testPatchTask3.getTaskName());
    }

    @Test
    void getPatchName() {
        testPatch1.setName("Lovely Home");
        testPatch2.setName("Flowery Corner");
        testPatch3.setName("Green Unseen");
        assertEquals("Lovely Home", testPatch1.getName());
        assertEquals("Flowery Corner", testPatch2.getName());
        assertEquals("Green Unseen", testPatch3.getName());
    }


    @Test
    void setPatchName() {
        testPatch1.setName("Lovely Home");
        testPatch2.setName("Flowery Corner");
        testPatch3.setName("Green Unseen");
        testPatch2.setName(testPatch1.getName());
        testPatch3.setName(testPatch1.getName());
        assertEquals("Lovely Home", testPatch1.getName());
        assertEquals("Lovely Home", testPatch2.getName());
        assertEquals("Lovely Home", testPatch3.getName());

    }

    @Test
    void getPatchId() {
        testPatch1.setPatchId(98);
        testPatch2.setPatchId(23);
        testPatch3.setPatchId(3);
        assertEquals(98, testPatch1.getPatchId());
        assertEquals(23, testPatch2.getPatchId());
        assertEquals(3, testPatch3.getPatchId());
    }

    @Test
    void setPatchId() {
        int test1 = 3345;
        int test2 = 35;
        int test3 = 3;
        int test4 = test1 + 2;
        int test5 = test2 + 345;
        int test6 = test3 + 34;
        testPatch1.setPatchId(test4);
        testPatch2.setPatchId(test5);
        testPatch3.setPatchId(test6);
        assertEquals(test1 + 2, testPatch1.getPatchId());
        assertEquals(test2 + 345, testPatch2.getPatchId());
        assertEquals(test3 + 34, testPatch3.getPatchId());
    }

    @Test
    void getGarden() {
        testPatch1.setGarden(testGarden1);
        testPatch2.setGarden(testGarden2);
        testPatch3.setGarden(testGarden3);
        assertEquals(1, testPatch1.getGarden().getGardenId());
        assertEquals(2, testPatch2.getGarden().getGardenId());
        assertEquals(3, testPatch3.getGarden().getGardenId());
    }

    @Test
    void setGarden() {
        testPatch1.setGarden(testGarden1);
        testPatch2.setGarden(testGarden2);
        testPatch3.setGarden(testGarden3);
        testPatch1.setGarden(testPatch3.getGarden());
        testPatch2.setGarden(testPatch3.getGarden());
        assertEquals(3, testPatch1.getGarden().getGardenId());
        assertEquals(3, testPatch2.getGarden().getGardenId());
        assertEquals(3, testPatch3.getGarden().getGardenId());
    }

}