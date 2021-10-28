package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class PatchTaskService {

    private PatchTaskRepository patchTaskRepository;
    private PatchService patchService;

    public PatchTaskService(PatchTaskRepository patchTaskRepository, PatchService patchService) {
        this.patchTaskRepository = patchTaskRepository;
        this.patchService = patchService;
    }

    public void savePatchTask(PatchTask patchTask) {
        patchTaskRepository.save(patchTask);
    }

    public List<PatchTaskDTO> getAllPatchTasks() {
        return patchTaskRepository.findAll().stream().map(this::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public List<PatchTaskDTO> getAllTasksByPatchId(int patchId) {
        List<PatchTask> tasks = patchTaskRepository.findAllBypatch_patchId(patchId);
        return tasks.stream().map(this::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public PatchTaskDTO convertToPatchTaskDTO(PatchTask patchTask) {
        PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
        patchTaskDTO.setTaskId(patchTask.getTaskId());
        patchTaskDTO.setTaskName(patchTask.getTaskName());
        patchTaskDTO.setTaskDescription(patchTask.getTaskDescription());
        patchTaskDTO.setDone(patchTask.getIsDone());
        patchTaskDTO.setPatchDTO(patchService.convertToPatchDTO(patchTask.getPatch()));
        return patchTaskDTO;
    }
}