package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import org.springframework.stereotype.Service;

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
    private GardenService gardenService; // TODO do really we want this GardenService here or higher up?

    public PatchService(PatchRepository patchRepository, GardenService gardenService) {
        this.patchRepository = patchRepository;
        this.gardenService = gardenService;
    }

    // TODO test whether the patchrepository automatically loads entire gardens that are foreign key
    public List<PatchDTO> getAllPatches() {
        List<Patch> patches = patchRepository.findAll();
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public PatchDTO getPatchById(int patchId) {
        Patch patch = patchRepository.getById(patchId);
        return convertToPatchDTO(patch);
    }

    public List<PatchDTO> getAllPatchesByGardenId(int gardenId) {
        List<Patch> patches = patchRepository.findAllBygarden_gardenId(gardenId);
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public PatchDTO convertToPatchDTO(Patch patch) {
        PatchDTO patchDTO = new PatchDTO();
        patchDTO.setPatchId(patch.getPatchId());
        patchDTO.setGardenDTO(gardenService.convertToGardenDTO(patch.getGarden()));
        return patchDTO;
    }
}
