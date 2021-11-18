package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.PatchTask;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class PatchTaskConverter {

    private final PatchConverter patchConverter;

    public PatchTaskConverter(PatchConverter patchConverter) {
        this.patchConverter = patchConverter;
    }

    public PatchTaskDTO convertToPatchTaskDTO(PatchTask patchTask) {
        PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
        patchTaskDTO.setTaskId(patchTask.getTaskId());
        patchTaskDTO.setTaskName(patchTask.getTaskName());
        patchTaskDTO.setTaskDescription(patchTask.getTaskDescription());
        patchTaskDTO.setDone(patchTask.getIsDone());
        patchTaskDTO.setPatchDTO(patchConverter.convertToPatchDTO(patchTask.getPatch()));
        return patchTaskDTO;
    }

    public PatchTask convertFromPatchTaskDTO(PatchTaskDTO patchTaskDTO) {
        PatchTask patchTask = new PatchTask();
        patchTask.setTaskId(patchTaskDTO.getTaskId());
        patchTask.setTaskName(patchTaskDTO.getTaskName());
        patchTask.setTaskDescription(patchTaskDTO.getTaskDescription());
        patchTask.setDone(patchTaskDTO.isDone());
        patchTask.setPatch(patchConverter.convertFromPatchDTO(patchTaskDTO.getPatchDTO()));
        return patchTask;
    }
}
