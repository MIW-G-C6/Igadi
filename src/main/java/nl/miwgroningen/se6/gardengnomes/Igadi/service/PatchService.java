package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

    public PatchDTO convertToPatchDTO(Patch patch) {
        PatchDTO patchDTO = new PatchDTO();
        patchDTO.setPatchId(patch.getPatchId());
        patchDTO.setCrop(patch.getCrop());
        patchDTO.setGardenDTO(gardenService.convertToGardenDTO(patch.getGarden()));
        return patchDTO;
    }

    public Patch convertFromPatchDTO(PatchDTO patchDTO, Garden garden) {
        Patch patch  = new Patch();
        patch.setPatchId(patchDTO.getPatchId());
        patch.setCrop(patchDTO.getCrop());
        patch.setGarden(garden);
        return patch;
    }

    public void savePatch(Patch patch) {
        patchRepository.save(patch);
    }
}