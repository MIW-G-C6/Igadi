/*
package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

*/
/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     In this controller, we created functions for interaction between the database and parts of our application where
 *     tasks are involved. This includes the gardenOverview page.
 *//*


@Controller
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping({"/tasks"})
    protected String showTaskOverview(Model model) {
        model.addAttribute("allTasks", taskRepository.findAll());
        return "taskOverview";
    }
}
*/
