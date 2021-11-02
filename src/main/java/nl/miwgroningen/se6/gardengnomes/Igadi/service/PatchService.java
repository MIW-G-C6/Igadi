package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tjerk Nagel
 * doel:
 */

@Service
public class PatchService {

    private PatchRepository patchRepository;
    private GardenService gardenService;
    private PatchTaskRepository patchTaskRepository;

    public PatchService(PatchRepository patchRepository,
                        PatchTaskRepository patchTaskRepository, GardenService gardenService) {
        this.patchRepository = patchRepository;
        this.gardenService = gardenService;
        this.patchTaskRepository = patchTaskRepository;
    }

    public List<PatchDTO> getAllPatches() {
        List<Patch> patches = patchRepository.findAll();
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public Patch getPatchById(int patchId) {
        Patch patch = patchRepository.getById(patchId);
        return patch;
    }

    public List<PatchDTO> getAllPatchesByGardenId(int gardenId) {
        List<Patch> patches = patchRepository.findAllBygarden_gardenId(gardenId);
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public ArrayList<Patch> findAllPatchesByGardenId(int gardenId) {
        ArrayList<Patch> patches = patchRepository.findAllBygarden_gardenId(gardenId);
        return patches;
    }

    public PatchDTO convertToPatchDTO(Patch patch) {
        PatchDTO patchDTO = new PatchDTO();
        patchDTO.setPatchId(patch.getPatchId());
        patchDTO.setGardenDTO(gardenService.convertToGardenDTO(patch.getGarden()));
        return patchDTO;
    }

    @Transactional
    public void deleteAllPatchesWithGarden(Patch patch){
        List<PatchTask> tasks = patchTaskRepository.findAllBypatch_patchId(patch.getPatchId());
        for(PatchTask task : tasks) {
            patchTaskRepository.delete(task);
        }
        patchRepository.delete(patch);
    }

    public void savePatch(Patch patch) {
        patchRepository.save(patch);
    }
}
