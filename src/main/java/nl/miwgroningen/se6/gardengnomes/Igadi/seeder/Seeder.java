package nl.miwgroningen.se6.gardengnomes.Igadi.seeder;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
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
 *     This is our seeder class, that is run every thing the project is started. It fills the database with entries if
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
    private final JoinGardenRequestService joinGardenRequestService;

    @Autowired
    public Seeder(GardenService gardenService, GardenTaskService gardenTaskService, PatchService patchService,
                  PatchTaskService patchTaskService, UserService userService, PasswordEncoder passwordEncoder,
                  GardenUserService gardenUserService, JoinGardenRequestService joinGardenRequestService) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
        this.patchService = patchService;
        this.patchTaskService = patchTaskService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.gardenUserService = gardenUserService;
        this.joinGardenRequestService = joinGardenRequestService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedGardens();
        seedUsers();
        seedGardenTasks();
        seedPatches();
        seedPatchTasks();
        seedGardenUsers();
        seedGardenRequests();
        seedAdmin();
        seedForPresentation();
    }

    public void seedUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if(users.isEmpty()) {
            String[] names = {"Jan Naaktgeboren", "Lisa van Veen", "Akim Hassous", "Frank Roelofsen", "Frederick in't Veld",
                    "Tigo Prins", "Saskia Bakker", "Evert de Boer", "Youssouf Mehmu", "Peter Straatman"};
            for(int i = 0; i < names.length; i++) {
                UserDTO userDTO = createUserSeed(names[i]);
                userService.saveUser(userDTO);
            }
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
        }
    }

    public void seedGardens() {
        List<GardenDTO> gardens = gardenService.getAllGardens();
        if(gardens.isEmpty()) {
            String[] gardenNames = {"Eden", "Noorder Plantsoentje", "De Groene Weelde", "Bij het Meertje",
                    "Bowsers Big Balloon Castle", "Het Dorpstuintje", "Samen in de Tuin", "Het Groene Kwartier",
                    "Achter de Kerk", "Howling Fjord", "Het Moeras"};
            String[] locations = {"Amsterdam", "Groningen", "Oudehaske", "Assen", "Haren", "Emmen", "Hengelo",
                    "Hoogeveen", "Meppel", "Leeuwarden", "Grootegast"};
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

    public void seedGardenRequests() {
        List<JoinGardenRequestDTO> requests = joinGardenRequestService.getAllRequests();
        if(requests.isEmpty()){
            ArrayList<UserDTO> testUsers = new ArrayList<>();
            UserDTO kynes = createUserSeed("Wim Naaktgeboren");
            UserDTO lydia = createUserSeed("Anne van Veen");
            UserDTO donald = createUserSeed("Klaas Hassous");
            UserDTO corey = createUserSeed("Wouter Roelofsen");
            UserDTO rupert = createUserSeed("Greetje in't Veld");
            userService.saveUser(kynes);
            userService.saveUser(lydia);
            userService.saveUser(donald);
            userService.saveUser(corey);
            userService.saveUser(rupert);
            testUsers.add(userService.findUserByUsername("Wim Naaktgeboren"));
            testUsers.add(userService.findUserByUsername("Anne van Veen"));
            testUsers.add(userService.findUserByUsername("Klaas Hassous"));
            testUsers.add(userService.findUserByUsername("Wouter Roelofsen"));
            testUsers.add(userService.findUserByUsername("Greetje in't Veld"));
            List<GardenDTO> gardens = gardenService.getAllGardens();
            for(GardenDTO gardenDTO : gardens) {
                for(UserDTO user : testUsers) {
                    JoinGardenRequestDTO joinGardenRequestDTO = new JoinGardenRequestDTO();
                    joinGardenRequestDTO.setStatus("pending");
                    joinGardenRequestDTO.setGardenDTO(gardenDTO);
                    joinGardenRequestDTO.setUserDTO(user);
                    joinGardenRequestService.saveRequest(joinGardenRequestDTO);
                }
            }
        }
    }

    public UserDTO createUserSeed(String name) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        String[] firstName = name.split(" ");
        /*int randomGarden = (int) (Math.random() * allGardens.size()) + 1;*/
        String email = firstName[0].toLowerCase() + "@hotmail.com";
        String password = firstName[0].toLowerCase() + "123";
        UserDTO user = new UserDTO();
        user.setUserName(name);
        user.setPassword1(passwordEncoder.encode(password));
        user.setUserEmail(email);
        /*user.setGarden(gardenService.getGardenById(randomGarden));*/
        return user;
    }

    public GardenUserDTO createGardenUserSeed(UserDTO userDTO, GardenDTO gardenDTO, String role) {
        GardenUserDTO gardenUserDTO = new GardenUserDTO();
        gardenUserDTO.setUserDTO(userDTO);
        gardenUserDTO.setGardenDTO(gardenDTO);
        gardenUserDTO.setRole(role);
        return gardenUserDTO;
    }

    public void seedAdmin() {
        boolean adminCreated =  userService.checkIfUserEmailExists("admin@admin.com");
        if(!adminCreated) {
            UserDTO admin = createUserSeed(UserRole.ADMIN);
            admin.setUserEmail("admin@admin.com");
            admin.setPassword1("admin");
            admin.setPassword1(passwordEncoder.encode(admin.getPassword1()));
            userService.saveUser(admin);
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
        String[] titles = {"Veeg het pad aan", "Haal onkruid weg", "Ruim troep op", "Repareer het hekje", "Verf het hekje", "Ponder",
                "Toast toasters", "Leeg de afvalbak"};
        for(int i = 0; i < titles.length; i++) {
            String description = titles[i].toLowerCase();
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
        String[] crops = {"rapen", "wortels", "aardappelen", "druiven", "pompoenen", "braamstruiken", "aardbei"};
        String[] patchNames = {"rechts achterin", "naast de eik", "het strookje"};
        for (int i = 0; i < patchesPerGarden; i++) {
            int randomCrops = (int)Math.floor(Math.random() * crops.length);
            PatchDTO patchDTO = new PatchDTO();
            patchDTO.setName(patchNames[i]);
            patchDTO.setCrop(crops[randomCrops]);
            patchDTO.setGardenDTO(gardenDTO);
            patches.add(patchDTO);
        }
        return patches;
    }

    public ArrayList<PatchTaskDTO> createPatchTaskSeed(PatchDTO patchDTO) {
        ArrayList<PatchTaskDTO> tasks = new ArrayList<>();
        String[] titles = {"Planten", "Bemesten", "Water geven", "Schoonmaken", "Snoeien", "Onkruid wieden", "Schoffelen", "Oogsten"};
        for(int i = 0; i < titles.length; i++) {
            String crop = patchDTO.getCrop();
            String title = titles[i];
            String description = "Het is tijd om te " + titles[i].toLowerCase();
            PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
            patchTaskDTO.setTaskName(title);
            patchTaskDTO.setTaskDescription(description);
            patchTaskDTO.setDone(false);
            patchTaskDTO.setPatchDTO(patchDTO);
            tasks.add(patchTaskDTO);
        }
        return tasks;
    }

    public void seedForPresentation() {

        UserDTO userDTO = createUserSeed("Lukas de Ruiter");
        int userId = userService.saveUser(userDTO);
        userDTO.setUserId(userId);

        GardenDTO gardenDTO = createGardenSeed("De vier wijken", "Groningen");
        int gardenId = gardenService.saveGarden(gardenDTO);
        gardenDTO.setGardenId(gardenId);

        GardenUserDTO gardenUserDTO = createGardenUserSeed(userDTO, gardenDTO, "gardenManager");
        gardenUserService.saveGardenUser(gardenUserDTO);

        ArrayList<PatchDTO> patches = createPatchSeed(gardenDTO);
        patches.get(0).setName("Links achterin");
        patches.get(1).setName("Naast de vijver");
        patches.get(2).setName("minimoestuin");

        patches.get(0).setCrop("Pompoen");
        patches.get(1).setCrop("Aardappel");
        patches.get(2).setCrop("Winterpeen");

        for(PatchDTO patchDTO : patches) {
            int patchId = patchService.savePatch(patchDTO);
            patchDTO.setPatchId(patchId);

            ArrayList<PatchTaskDTO> tasks = new ArrayList<>();
            String[] tasksList = {"Aanvegen", "Bemesten", "Water geven", "Schoffelen", "Wieden", "Zaaien", "Oogsten", "Opsteken"};
            String[] description = new String[] {"Veeg de bladeren bij elkaar", "Geef deze perk extra voeding","Dit perkje heeft extra water nodig", "Maak de grond los en woel om",
                    "Verwijder het onkruid", "Plant zaadjes op 10 cm afstand van elkaar", "Haal de groente uit de tuin of pluk het fruit",
                    "Zet de plant vast aan een rek, zodat deze niet omvalt"};

            for(int i = 0; i < tasksList.length; i++) {
                String title = tasksList[i];
                String description2 = description[i];
                PatchTaskDTO patchTaskDTO = new PatchTaskDTO();
                patchTaskDTO.setTaskName(title);
                patchTaskDTO.setTaskDescription(description2);
                patchTaskDTO.setDone(false);
                patchTaskDTO.setPatchDTO(patchDTO);
                patchTaskService.savePatchTask(patchTaskDTO);
            }

        }
    }
}
