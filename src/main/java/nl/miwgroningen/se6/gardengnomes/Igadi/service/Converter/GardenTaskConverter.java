package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.GardenTask;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class GardenTaskConverter {

    private final GardenConverter gardenConverter;

    public GardenTaskConverter(GardenConverter gardenConverter) {
        this.gardenConverter = gardenConverter;
    }

    public GardenTaskDTO convertToGardenTaskDTO(GardenTask gardenTask) {
        GardenTaskDTO gardenTaskDTO = new GardenTaskDTO();
        gardenTaskDTO.setTaskId(gardenTask.getTaskId());
        gardenTaskDTO.setTaskName(gardenTask.getTaskName());
        gardenTaskDTO.setTaskDescription(gardenTask.getTaskDescription());
        gardenTaskDTO.setDone(gardenTask.getIsDone());
        gardenTaskDTO.setGardenDTO(gardenConverter.convertToGardenDTO(gardenTask.getGarden()));
        return gardenTaskDTO;
    }

    public GardenTask convertFromGardenTaskDTO(GardenTaskDTO gardenTaskDTO) {
        GardenTask gardenTask = new GardenTask();
        gardenTask.setTaskId(gardenTaskDTO.getTaskId());
        gardenTask.setTaskName(gardenTaskDTO.getTaskName());
        gardenTask.setTaskDescription(gardenTaskDTO.getTaskDescription());
        gardenTask.setDone(gardenTaskDTO.isDone());
        gardenTask.setGarden(gardenConverter.convertFromGardenDTO(gardenTaskDTO.getGardenDTO()));
        return gardenTask;
    }
}
