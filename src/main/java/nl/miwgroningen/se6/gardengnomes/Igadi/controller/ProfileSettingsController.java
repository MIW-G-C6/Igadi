package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author Annemarleen Bosma <makeItWokr2021@annemarleenbosma.nl>
 *
 * This page controlls the profile page, especially supporting the option to change settings
 *
 */

@Controller
public class ProfileSettingsController {

    private UserService userService;

    public ProfileSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profileSettings")
    protected String showProfileSettings(Model model, @AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.getUserById(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("buttonText", "Save changes");
//        model.addAttribute("userEmail", userDTO.getUserEmail());

        return "profileSettings";
    }

    @GetMapping("/profileSettings/new")
    protected String showChangedProfileSettings(Model model,
                                       @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {

        userService.getUserById(user.getUserId());

        model.addAttribute("originalEmail");
        model.addAttribute("newEmail");

        return "profileSettings";
    }
//
//    @PostMapping("profileSettings/new")
//    protected String changeUserProfileSettings(@ModelAttribute("user") UserDTO userDTO, BindingResult result,
//                                               RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
//
//
//        if (!result.hasErrors()) {
//            String originalEmail = user.getUserEmail();
//
////            boolean duplicateEmail = userService.checkIfUserEmailExists(userDTO.getUserEmail());
//            boolean duplicateEmail = userService.checkIfUserEmailExists(originalEmail);
//
//            String message = "on my way to the -if duplicate Email-";
//
//            if (duplicateEmail) {
//                System.out.println("The input email is in the db");
//
//                // now I want to get the input of the second inputfield of the email
//
//                userDTO.setUserEmail(userDTO.getUserEmail()); // here the input of the new email has to be put
//                userDTO.setUserName(user.getUserName());
//                userDTO.setPassword1(user.getUserPassword());
//                userDTO.setPassword2(user.getUserPassword());
//
////                if(!userDTO.getUserEmail().equals()) {
////                    System.out.println("going on");
////                }
////                userDTO.setUserEmail(userDTO.getUserEmail());
//                userService.saveUser(userDTO);
//            try {
//                userService.saveUser(userDTO);
//                System.out.println(" in the try of the saveUser");
//
//                return "redirect:/gardens";
//            } catch (Exception ex) {
//                System.out.println("this is the exception catch from changeUserProfileSettings");
//            }
//            }
//            redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
//            return "redirect:/";
//        }
//        return "gardens";
//    }
}