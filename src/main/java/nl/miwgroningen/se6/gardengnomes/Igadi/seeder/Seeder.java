package nl.miwgroningen.se6.gardengnomes.Igadi.seeder;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class Seeder {

    UserService userService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public Seeder(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
    }

    public void seedUsers() {
        List<UserDTO> users = userService.getAllUsers();

        if(users.isEmpty()) {

            userService.saveUser(createUserSeed("Jan", "jan123", "jan@hotmail.com"));
            userService.saveUser(createUserSeed("Pete", "pete123", "pete@hotmail.com"));
            userService.saveUser(createUserSeed("Hank", "hank123", "hank@hotmail.com"));
            userService.saveUser(createUserSeed("Cees", "cees123", "cees@hotmail.com"));
            userService.saveUser(createUserSeed("Rodger", "rodger123", "rodger@hotmail.com"));
            userService.saveUser(createUserSeed("Michael", "michael123", "michael@hotmail.com"));
            userService.saveUser(createUserSeed("Billy", "billy123", "billy@hotmail.com"));
            userService.saveUser(createUserSeed("Luigi", "luigi123", "luigi@hotmail.com"));
            userService.saveUser(createUserSeed("Phillip", "phillip123", "phillip@hotmail.com"));
            userService.saveUser(createUserSeed("Peter", "peter123", "peter@hotmail.com"));
        }
    }

    public User createUserSeed(String name, String password, String email) {
        User user = new User();
        user.setUserName(name);
        user.setUserPassword(passwordEncoder.encode(password));
        user.setUserEmail(email);
        return user;
    }
}
