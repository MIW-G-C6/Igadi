package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GardenService {

    private final GardenRepository gardenRepository;
    private final AuthorizationHelper authorizationHelper;
    private final GardenConverter gardenConverter;

    public GardenService(GardenRepository gardenRepository, AuthorizationHelper authorizationHelper,
                         GardenConverter gardenConverter) {
        this.gardenRepository = gardenRepository;
        this.authorizationHelper = authorizationHelper;
        this.gardenConverter = gardenConverter;
    }

    public List<GardenDTO> getAllGardens() {
        return gardenRepository.findAll().stream().map(gardenConverter::convertToGardenDTO).collect(Collectors.toList());
    }

    public List<GardenDTO> findAllGardensByUserId(int userId) {
        return gardenRepository.findAllByUserId(userId).stream().map(gardenConverter::convertToGardenDTO).collect(Collectors.toList());
    }

    public GardenDTO getGardenById(int gardenId) {
        Garden garden = gardenRepository.getById(gardenId);
        return gardenConverter.convertToGardenDTO(garden);
    }

    public int saveGarden(GardenDTO gardenDTO) {
        Garden garden = gardenConverter.convertFromGardenDTO(gardenDTO);
        gardenRepository.save(garden);
        return garden.getGardenId();
    }

    public void userDeleteGarden(int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            deleteGardenById(gardenId);
        } else {
            throw new SecurityException("You are not allowed to delete this garden.");
        }
    }

    public void deleteGardenById(int gardenId) {
        gardenRepository.deleteById(gardenId);
    }

    public List<GardenDTO> findByNameContains(String keyword){
        List<Garden> foundList = gardenRepository.findByGardenNameContains(keyword);
        List<GardenDTO> gardenDTOs = new ArrayList<>();
        for(Garden garden : foundList) {
            gardenDTOs.add(gardenConverter.convertToGardenDTO(garden));
        }
        return gardenDTOs;
    }
}