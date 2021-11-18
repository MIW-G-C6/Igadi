package nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting.Converter.GardenConverter;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting.Converter.PatchConverter;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting.Converter.PatchTaskConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskPatchGardenConvertionTest {

    PatchTaskConverter patchTaskConverter;
    PatchConverter patchConverter;
    GardenConverter gardenConverter;

    @BeforeEach
    void setUp() {
        gardenConverter = new GardenConverter();
        patchConverter = new PatchConverter(gardenConverter);
        patchTaskConverter = new PatchTaskConverter(patchConverter);
    }

    @Test
    void testGardenConvertion() {
        Garden garden = new Garden();
        garden.setGardenId(1);
        garden.setGardenName("eden");
        garden.setLocation("New York");
        GardenDTO gardenDTO = gardenConverter.convertToGardenDTO(garden);
        assertEquals("eden", gardenDTO.getGardenName());
        assertEquals(1, gardenDTO.getGardenId());
        assertEquals("New York", gardenDTO.getLocation());
    }

    @Test
    void testPatchConvertion() {
        Garden garden = new Garden();
        garden.setGardenId(1);
        garden.setGardenName("eden");
        garden.setLocation("New York");
        Patch patch = new Patch();
        patch.setGarden(garden);
        patch.setPatchId(2);
        patch.setCrop("carrots");
        PatchDTO patchDTO = patchConverter.convertToPatchDTO(patch);
        assertEquals("eden", patchDTO.getGardenDTO().getGardenName());
        assertEquals(1, patchDTO.getGardenDTO().getGardenId());
        assertEquals("New York", patchDTO.getGardenDTO().getLocation());
        assertEquals(2, patchDTO.getPatchId());
        assertEquals("carrots", patchDTO.getCrop());
    }

    @Test
    void testPatchTaskConvertion() {
        Garden garden = new Garden();
        garden.setGardenId(1);
        garden.setGardenName("eden");
        garden.setLocation("New York");
        Patch patch = new Patch();
        patch.setGarden(garden);
        patch.setPatchId(2);
        patch.setCrop("carrots");
        PatchTask patchTask = new PatchTask();
        patchTask.setTaskId(3);
        patchTask.setTaskName("taskName");
        patchTask.setTaskDescription("testDescription");
        patchTask.setDone(false);
        patchTask.setPatch(patch);
        PatchTaskDTO patchTaskDTO = patchTaskConverter.convertToPatchTaskDTO(patchTask);
        assertEquals("eden", patchTaskDTO.getPatchDTO().getGardenDTO().getGardenName());
        assertEquals(1, patchTaskDTO.getPatchDTO().getGardenDTO().getGardenId());
        assertEquals("New York", patchTaskDTO.getPatchDTO().getGardenDTO().getLocation());
        assertEquals(2, patchTaskDTO.getPatchDTO().getPatchId());
        assertEquals("carrots", patchTaskDTO.getPatchDTO().getCrop());
        assertEquals(3, patchTaskDTO.getTaskId());
        assertEquals("taskName", patchTaskDTO.getTaskName());
        assertEquals("testDescription", patchTaskDTO.getTaskDescription());
        assertEquals(false, patchTaskDTO.isDone());
    }
}