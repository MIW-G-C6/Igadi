package nl.miwgroningen.se6.gardengnomes.Igadi.testing.integrationtesting;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.UserRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@SpringBootTest
public class UserServiceIntegrationTest {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceIntegrationTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    void testUserRegistration() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("test1");
        userDTO.setUserEmail("test1@gmail.com");
        userDTO.setPassword1("test123");

        userService.saveUser(userDTO);

        Optional<User> dbTest1 = userRepository.findUserByUserName("test1");
        Optional<User> dbTest2 = userRepository.findUserByUserEmail("test1@gmail.com");

        userService.deleteUser(userService.getUserById(dbTest1.get().getUserId()));

        assertNotNull(dbTest1);
        assertNotNull(dbTest2);

        assertEquals("test1", dbTest1.get().getUserName());
        assertEquals("test1@gmail.com", dbTest2.get().getUserEmail());
    }
}
