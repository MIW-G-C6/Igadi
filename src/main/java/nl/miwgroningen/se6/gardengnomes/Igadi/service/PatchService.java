package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchTaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenConverter;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.PatchConverter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tjerk Nagel
 * doel:
 */

@Service
public class PatchService {

    private final PatchRepository patchRepository;
    private final GardenService gardenService;
    private final PatchTaskRepository patchTaskRepository;
    private final GardenUserService gardenUserService;
    private final AuthorizationHelper authorizationHelper;
    private final GardenConverter gardenConverter;
    private final PatchConverter patchConverter;

    public PatchService(PatchRepository patchRepository, GardenUserService gardenUserService,
                        PatchTaskRepository patchTaskRepository, GardenService gardenService,
                        AuthorizationHelper authorizationHelper, GardenConverter gardenConverter,
                        PatchConverter patchConverter) {
        this.patchRepository = patchRepository;
        this.gardenService = gardenService;
        this.patchTaskRepository = patchTaskRepository;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
        this.gardenConverter = gardenConverter;
        this.patchConverter = patchConverter;
    }

    public List<PatchDTO> getAllPatches() {
        List<Patch> patches = patchRepository.findAll();
        return patches.stream().map(patchConverter::convertToPatchDTO).collect(Collectors.toList());
    }

    public PatchDTO getPatchById(int patchId) {
        PatchDTO patchDTO = patchConverter.convertToPatchDTO(patchRepository.getById(patchId));
        return patchDTO;
    }

    public List<PatchDTO> findAllPatchesByGardenId(int gardenId) {
        List<Patch> patches = patchRepository.findAllBygarden_gardenId(gardenId);
        return patches.stream().map(patchConverter::convertToPatchDTO).collect(Collectors.toList());
    }

    public void savePatch(PatchDTO patchDTO) {
        patchRepository.save(patchConverter.convertFromPatchDTO(patchDTO));
    }

    public void userSavePatch(PatchDTO patchDTO, int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            savePatch(patchDTO);
        } else {
            throw new SecurityException("You are not allowed to save this patch.");
        }
    }

    public int findGardenIdByPatchId (int patchId) {
        return patchRepository.findGardenIdByPatchId(patchId).orElseThrow(() ->
                new NullPointerException("No patch with this patchId was found."));
    }

    public void userDeletePatch(int userId, PatchDTO patchDTO) {
        if (authorizationHelper.isUserGardenManager(userId, patchDTO.getGardenDTO().getGardenId())) {
            patchRepository.delete(patchConverter.convertFromPatchDTO(patchDTO));
        } else {
            throw new SecurityException("You are not allowed to delete this patch.");
        }
    }
}