package nl.miwgroningen.se6.gardengnomes.Igadi.seeder;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This is our seeder class, that is run every thing the project is started. In fills the database with entries if
 *     no entries are present. To do this, we created a createSeed function for each class, as normally the classes are
 *     saved using a DTO object from the front-end.
 */

@Component
public class Seeder {

    public static final int patchesPerGarden = 3;
    private final GardenService gardenService;
    private final GardenTaskService gardenTaskService;
    private final PatchService patchService;
    private final PatchTaskService patchTaskService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final GardenUserService gardenUserService;

    @Autowired
    public Seeder(GardenService gardenService, GardenTaskService gardenTaskService, PatchService patchService,
                  PatchTaskService patchTaskService, UserService userService, PasswordEncoder passwordEncoder,
                  GardenUserService gardenUserService) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
        this.patchService = patchService;
        this.patchTaskService = patchTaskService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.gardenUserService = gardenUserService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedGardens();
        seedUsers();
        seedGardenTasks();
        seedPatches();
        seedPatchTasks();
        seedGardenUsers();
    }

    public void seedUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if(users.isEmpty()) {
            String[] names = {"Jan", "Pete", "Hank", "Cees", "Rodger", "Michael", "Billy", "Luigi", "Phillip", "Peter"};
            for(int i = 0; i < names.length; i++) {
                UserDTO userDTO = createUserSeed(names[i]);
                userService.saveUser(userDTO);
                /*UserDTO newUserDTO = userService.findUserByUsername(userDTO.getUserName());
                GardenUserDTO gardenUserDTO = createGardenUserSeed(newUserDTO);
                gardenUserService.saveGardenUser(gardenUserDTO);*/
            }
            /*seedChad();*/
        }
    }

    public void seedGardenUsers() {
        List<GardenUserDTO> gardenUsers = gardenUserService.getAllGardenUsers();
        if(gardenUsers.isEmpty()) {
            List<UserDTO> users = userService.getAllUsers();
            List<GardenDTO> gardens = gardenService.getAllGardens();
            for (int i = 0; i < users.size(); i++) {
                GardenUserDTO gardenUserDTO1 = createGardenUserSeed(users.get(i), gardens.get(i), UserRole.GARDEN_MANAGER);
                gardenUserService.saveGardenUser(gardenUserDTO1);
                GardenUserDTO gardenUserDTO2 = createGardenUserSeed(users.get(i), gardens.get(i+1), UserRole.GARDENER);
                gardenUserService.saveGardenUser(gardenUserDTO2);
            }
            seedChad();
        }
    }

    public void seedGardens() {
        List<GardenDTO> gardens = gardenService.getAllGardens();
        if(gardens.isEmpty()) {
            String[] gardenNames = {"Eden", "Hanging Gardens", "Central Park", "Madison Square Garden",
                    "Bowsers Big Balloon Castle", "Orange County Park", "Red Reindeer Square", "Thousand needles",
                    "The Barrens", "Howling Fjord", "Extra Garden"};
            String[] locations = {"New York", "Hollywood", "OudeHaske", "London", "Toronto", "Buffalo", "Orlando",
                    "Oxford", "Berlin", "Caketown", "Groningen"};
            for(int i = 0; i < gardenNames.length; i++) {
                GardenDTO gardenDTO = createGardenSeed(gardenNames[i], locations[i]);
                gardenService.saveGarden(gardenDTO);
            }
        }
    }

    public void seedGardenTasks() {
        List<GardenTaskDTO> gardenTasks = gardenTaskService.getAllGardenTasks();
        if(gardenTasks.isEmpty()) {
            List<GardenDTO> allGardens = gardenService.getAllGardens();
            for (GardenDTO garden : allGardens) {
                ArrayList<GardenTaskDTO> tasks = createGardenTaskSeed(garden);
                for(GardenTaskDTO task: tasks) {
                    gardenTaskService.saveGardenTask(task);
                }
            }
        }
    }

    public void seedPatches() {
        List<PatchDTO> patches = patchService.getAllPatches();
        if(patches.isEmpty()) {
            List<GardenDTO> allGardens = gardenService.getAllGardens();
            for (GardenDTO garden : allGardens) {
                GardenDTO realGarden = new GardenDTO();
                realGarden.setGardenId(garden.getGardenId());
                realGarden.setGardenName(garden.getGardenName());
                ArrayList<PatchDTO> patchesPerGarden = createPatchSeed(realGarden);
                for (PatchDTO patch : patchesPerGarden) {
                    patchService.savePatch(patch);
                }
            }
        }
    }

    public void seedPatchTasks() {
        List<PatchTaskDTO> patchTasks = patchTaskService.getAllPatchTasks();
        if(patchTasks.isEmpty()) {
            List<PatchDTO> allPatches = patchService.getAllPatches();
            for(PatchDTO patch : allPatches) {
                PatchDTO realPatch = patchService.getPatchById(patch.getPatchId());
                ArrayList<PatchTaskDTO> allTasksForThisPatch = createPatchTaskSeed(realPatch);
                for(PatchTaskDTO task : allTasksForThisPatch) {
                    patchTaskService.savePatchTask(task);
                }
            }
        }
    }

    public UserDTO createUserSeed(String name) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        /*int randomGarden = (int) (Math.random() * allGardens.size()) + 1;*/
        String email = name.toLowerCase() + "@hotmail.com";
        String password = name.toLowerCase() + "123";
        UserDTO user = new UserDTO();
        user.setUserName(name);
        user.setPassword1(passwordEncoder.encode(password));
        user.setUserEmail(email);
        /*user.setGarden(gardenService.getGardenById(randomGarden));*/
        return user;
    }

    /*public GardenUserDTO createGardenUserSeed(UserDTO userDTO) {
        GardenUserDTO gardenUserDTO = new GardenUserDTO();
        gardenUserDTO.setUserDTO(userDTO);
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        int randomGarden = (int) (Math.random() * allGardens.size()) + 1;
        gardenUserDTO.setGardenDTO(allGardens.stream().filter(x -> x.getGardenId() == randomGarden).findFirst().get());
        String[] roles = new String[] {UserRole.GARDENER, UserRole.GARDEN_MANAGER};
        int randomRole = (int) (Math.random() * roles.length);
        gardenUserDTO.setRole(roles[randomRole]);
        return gardenUserDTO;
    }*/

    public GardenUserDTO createGardenUserSeed(UserDTO userDTO, GardenDTO gardenDTO, String role) {
        GardenUserDTO gardenUserDTO = new GardenUserDTO();
        gardenUserDTO.setUserDTO(userDTO);
        gardenUserDTO.setGardenDTO(gardenDTO);
        gardenUserDTO.setRole(role);
        return gardenUserDTO;
    }

    public void seedChad() {
        UserDTO chad = createUserSeed("Chad");
        userService.saveUser(chad);
        UserDTO newChad = userService.findUserByUsername("Chad");
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        for (GardenDTO gardenDTO : allGardens) {
            GardenUserDTO gardenUserChad = createGardenUserSeed(newChad, gardenDTO, UserRole.GARDEN_MANAGER);
            gardenUserService.saveGardenUser(gardenUserChad);
        }
    }

    public GardenDTO createGardenSeed(String gardenName, String location) {
        GardenDTO gardenDTO = new GardenDTO();
        gardenDTO.setGardenName(gardenName);
        gardenDTO.setLocation(location);
        return gardenDTO;
    }

    public ArrayList<GardenTaskDTO> createGardenTaskSeed(GardenDTO gardenDTO) {
        ArrayList<GardenTaskDTO> tasks = new ArrayList<>();
        String[] titles = {"Sweep the paths", "Remove weeds", "Gather trash", "Repair fences", "Paint fences", "Ponder",
                "Toast toasters", "Empty trashcans"};
        for(int i = 0; i < titles.length; i++) {
            String description = "Please " + titles[i].toLowerCase() + " today";
            GardenTaskDTO gardenTaskDTO = new GardenTaskDTO();
            gardenTaskDTO.setTaskName(titles[i]);
            gardenTaskDTO.setTaskDescription(description);
            gardenTaskDTO.setDone(false);
            gardenTaskDTO.setGardenDTO(gardenDTO);
            tasks.add(gardenTaskDTO);
        }
        return tasks;
    }

    public ArrayList<PatchDTO> createPatchSeed(GardenDTO gardenDTO) {
        ArrayList<PatchDTO> patches = new ArrayList<>();
        String[] crops = {"turnips", "carrots", "potatoes", "grapes", "pumpkins", "berry bushes", "strawberries"};
        for (int i = 0; i < patchesPerGarden; i++) {
            int randomCrops = (int)Math.floor(Math.random() * crops.length);
            PatchDTO patchDTO = new PatchDTO();
            patchDTO.setCrop(crops[randomCrops]);
            patchDTO.setGardenDTO(gardenDTO);
            patches.add(patchDTO);
        }
        return patches;
    }

    public ArrayList<PatchTaskDTO> createPatchTaskSeed(PatchDTO patchDTO) {
        ArrayList<PatchTaskDTO> tasks = new ArrayList<>();
        String[] titles = {"Plant", "Fertilize", "Water", "Clean", "Prune", "Weed", "Hoe", "Harvest"};
        for(int i = 0; i < titles.length; i++) {
            String crop = patchDTO.getCrop();
            String title = titles[i] + " the " + crop;
            String description = "Please " + titles[i].toLowerCase() + " the " + crop + " soon";
            PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
            patchTaskDTO.setTaskName(title);
            patchTaskDTO.setTaskDescription(description);
            patchTaskDTO.setDone(false);
            patchTaskDTO.setPatchDTO(patchDTO);
            tasks.add(patchTaskDTO);
        }
        return tasks;
    }
}