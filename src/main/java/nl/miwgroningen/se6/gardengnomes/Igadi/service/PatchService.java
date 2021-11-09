package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private GardenUserService gardenUserService;
    private AuthorizationHelper authorizationHelper;

    public PatchService(PatchRepository patchRepository, GardenUserService gardenUserService,
                        PatchTaskRepository patchTaskRepository, GardenService gardenService,
                        AuthorizationHelper authorizationHelper) {
        this.patchRepository = patchRepository;
        this.gardenService = gardenService;
        this.patchTaskRepository = patchTaskRepository;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
    }

    public List<PatchDTO> getAllPatches() {
        List<Patch> patches = patchRepository.findAll();
        return patches.stream().map(this::convertToPatchDTO).collect(Collectors.toList());
    }

    public Patch getPatchById(int patchId) {
        Patch patch = patchRepository.getById(patchId);
        return patch;
    }

    public List<PatchDTO> findAllPatchesByGardenId(int gardenId) {
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

    public void userSavePatch(Patch patch, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            savePatch(patch);
        } else {
            throw new SecurityException("You are not allowed to save this patch.");
        }
    }

    public int findGardenIdByPatchId (int patchId) {
        return patchRepository.findGardenIdByPatchId(patchId).orElseThrow(() ->
                new EntityNotFoundException("No patch with this patchId was found."));
    }
}