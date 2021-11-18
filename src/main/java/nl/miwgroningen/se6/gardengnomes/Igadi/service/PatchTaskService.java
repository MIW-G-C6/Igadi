package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting.Converter.PatchTaskConverter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class PatchTaskService {

    private final PatchTaskRepository patchTaskRepository;
    private final AuthorizationHelper authorizationHelper;
    private final PatchTaskConverter patchTaskConverter;

    public PatchTaskService(PatchTaskRepository patchTaskRepository, AuthorizationHelper authorizationHelper,
                            PatchTaskConverter patchTaskConverter) {
        this.patchTaskRepository = patchTaskRepository;
        this.authorizationHelper = authorizationHelper;
        this.patchTaskConverter = patchTaskConverter;
    }

    public void savePatchTask(PatchTaskDTO patchTaskDTO) {
        patchTaskRepository.save(patchTaskConverter.convertFromPatchTaskDTO(patchTaskDTO));
    }

    public void userSavePatchTask(PatchTaskDTO patchTaskDTO, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            savePatchTask(patchTaskDTO);
        } else {
            throw new SecurityException("You are not allowed to save this patch task.");
        }
    }

    public List<PatchTaskDTO> getAllPatchTasks() {
        return patchTaskRepository.findAll().stream().map(patchTaskConverter::convertToPatchTaskDTO)
                .collect(Collectors.toList());
    }

    public List<PatchTaskDTO> getAllTasksByPatchId(int patchId) {
        List<PatchTask> tasks = patchTaskRepository.findAllBypatch_patchId(patchId);
        return tasks.stream().map(patchTaskConverter::convertToPatchTaskDTO).collect(Collectors.toList());
    }

    public void userDeletePatchTask(int userId, PatchTaskDTO patchTaskDTO) {
        if (authorizationHelper.isUserGardenManager(userId, patchTaskDTO.getPatchDTO().getGardenDTO().getGardenId())) {
            patchTaskRepository.delete(patchTaskConverter.convertFromPatchTaskDTO(patchTaskDTO));
        } else {
            throw new SecurityException("You are not allowed to delete this task.");
        }
    }

    public PatchTaskDTO getPatchTaskById(int patchTaskId) {
        return patchTaskConverter.convertToPatchTaskDTO(patchTaskRepository.getById(patchTaskId));
    }

    public void userSetDonePatchTask(PatchTaskDTO patchTaskDTO, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId) || authorizationHelper
                .isUserGardenMember(userId, gardenId)) {
            savePatchTask(patchTaskDTO);
        } else {
            throw new SecurityException("You are not allowed to complete this patch task.");
        }
    }
}