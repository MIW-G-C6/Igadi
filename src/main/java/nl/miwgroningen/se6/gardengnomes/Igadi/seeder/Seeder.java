package nl.miwgroningen.se6.gardengnomes.Igadi.seeder;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    public Seeder(GardenService gardenService, GardenTaskService gardenTaskService, PatchService patchService,
                  PatchTaskService patchTaskService, UserService userService, PasswordEncoder passwordEncoder) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
        this.patchService = patchService;
        this.patchTaskService = patchTaskService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
        seedGardens();
        seedGardenTasks();
        seedPatches();
        seedPatchTasks();
    }

    public void seedUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if(users.isEmpty()) {
            String[] names = {"Jan", "Pete", "Hank", "Cees", "Rodger", "Michael", "Billy", "Luigi", "Phillip", "Peter"};
            for(int i = 0; i < names.length; i++) {
                User user = createUserSeed(names[i]);
                userService.saveUser(user);
            }
        }
    }

    public void seedGardens() {
        List<GardenDTO> gardens = gardenService.getAllGardens();
        if(gardens.isEmpty()) {
            String[] gardenNames = {"Eden", "Hanging Gardens", "Central Park", "Madison Square Garden",
                    "Bowsers Big Balloon Castle", "Orange County Park", "Red Reindeer Square", "Thousand needles",
                    "The Barrens", "Howling Fjord"};
            for(int i = 0; i < gardenNames.length; i++) {
                Garden garden = createGardenSeed(gardenNames[i]);
                gardenService.saveGarden(garden);
            }
        }
    }

    public void seedGardenTasks() {
        List<GardenTaskDTO> gardenTasks = gardenTaskService.getAllGardenTasks();
        if(gardenTasks.isEmpty()) {
            List<GardenDTO> allGardens = gardenService.getAllGardens();
            for (GardenDTO garden : allGardens) {
                Garden realGarden = new Garden();
                realGarden.setGardenId(garden.getGardenId());
                realGarden.setGardenName(garden.getGardenName());
                ArrayList<GardenTask> tasks = createGardenTaskSeed(realGarden);
                for(GardenTask task: tasks) {
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
                Garden realGarden = new Garden();
                realGarden.setGardenId(garden.getGardenId());
                realGarden.setGardenName(garden.getGardenName());
                ArrayList<Patch> patchesPerGarden = createPatchSeed(realGarden);
                for (Patch patch : patchesPerGarden) {
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
                Patch realPatch = patchService.getPatchById(patch.getPatchId());
                ArrayList<PatchTask> allTasksForThisPatch = createPatchTaskSeed(realPatch);
                for(PatchTask task : allTasksForThisPatch) {
                    patchTaskService.savePatchTask(task);
                }
            }
        }
    }

    public User createUserSeed(String name) {
        String email = name.toLowerCase(Locale.ROOT) + "@hotmail.com";
        String password = name.toLowerCase(Locale.ROOT) + "123";
        User user = new User();
        user.setUserName(name);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setUserEmail(email);
        return user;
    }

    public Garden createGardenSeed(String gardenName) {
        Garden garden = new Garden();
        garden.setGardenName(gardenName);
        return garden;
    }

    public ArrayList<GardenTask> createGardenTaskSeed(Garden garden) {
        ArrayList<GardenTask> tasks = new ArrayList<>();
        String[] titles = {"Sweep the paths", "Remove weeds", "Gather trash", "Repair fences", "Paint fences", "Ponder",
                "Toast toasters", "Empty trashcans"};
        for(int i = 0; i < titles.length; i++) {
            String description = "Please " + titles[i].toLowerCase(Locale.ROOT) + " today";
            GardenTask gardenTask = new GardenTask();
            gardenTask.setTaskName(titles[i]);
            gardenTask.setTaskDescription(description);
            gardenTask.setDone(false);
            gardenTask.setGarden(garden);
            tasks.add(gardenTask);
        }
        return tasks;
    }

    public ArrayList<Patch> createPatchSeed(Garden garden) {
        ArrayList<Patch> patches = new ArrayList<>();
        for (int i = 0; i < patchesPerGarden; i++) {
            Patch patch = new Patch();
            patch.setGarden(garden);
            patches.add(patch);

        }
        return patches;
    }

    public ArrayList<PatchTask> createPatchTaskSeed(Patch patch) {
        ArrayList<PatchTask> tasks = new ArrayList<>();
        String[] titles = {"Plant", "Fertilize", "Water", "Clean", "Prune", "Weed", "Hoe", "Harvest"};
        String[] crops = {"turnips", "carrots", "potatoes", "grapes", "pumpkins", "berry bushes", "strawberries"};
        int randomCrops = (int)Math.floor(Math.random() * crops.length);

        for(int i = 0; i < titles.length; i++) {
            String title = titles[i] + " the " + crops[randomCrops];
            String description = "Please " + titles[i].toLowerCase() + " the " + crops[randomCrops] + " soon";
            PatchTask patchTask = new PatchTask();
            patchTask.setTaskName(title);
            patchTask.setTaskDescription(description);
            patchTask.setDone(false);
            patchTask.setPatch(patch);
            tasks.add(patchTask);
        }
        return tasks;
    }
}