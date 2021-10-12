package nl.miwgroningen.se6.gardengnomes.Igadi.service;

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

    public List<PatchDTO> getAllPatches() {
        List<Patch> patches = patchRepository.findAll();
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public List<PatchDTO> getAllPatchesByGardenId(int gardenId) {
        List<Integer> searchIds = new ArrayList<>();
        searchIds.add(gardenId);
        return patchRepository.findAllById(searchIds).stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public PatchDTO convertToPatchDTO(Patch patch) {
        PatchDTO patchDTO = new PatchDTO();
        patchDTO.setPatchId(patch.getPatchId());
        patchDTO.setGardenDTO(gardenService.convertToGardenDTO(patch.getGarden()));
        return patchDTO;
    }
}
