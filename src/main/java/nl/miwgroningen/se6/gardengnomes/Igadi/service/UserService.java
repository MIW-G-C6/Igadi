package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This service is meant for actions between the userRepository and the UserDTO/User model. Several methods are
 *     overwritten from UserDetailsService, including the loadByUsername method. This does not look at usernames, but
 *     at email addresses.
 */

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserRole(user.getUserRole());
        userDTO.setUserEmail(user.getUserEmail());
        return userDTO;
    }

    public User getUserById(int userId) {
        return userRepository.getById(userId);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean checkIfUserNameExists(String userName) {
        boolean userNameIsInDatabase = false;
        List<UserDTO> users = this.getAllUsers();
        for (UserDTO user : users) {
            if (user.getUserName().equals(userName)) {
                userNameIsInDatabase = true;
                break;
            }
        }
        return userNameIsInDatabase;
    }

    public boolean checkIfUserEmailExists(String userEmail) {
        boolean userEmailIsInDatabase = false;
        List<UserDTO> users = this.getAllUsers();
        for (UserDTO user : users) {
            if (user.getUserEmail().equals(userEmail)) {
                userEmailIsInDatabase = true;
                break;
            }
        }
        return userEmailIsInDatabase;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByUserEmail(s).orElseThrow(
                () -> new UsernameNotFoundException("Email address " + s + " was not found!")
        );
    }

    public User findUserByGardenId(int gardenId) {
        return userRepository.findUserBygarden_gardenId(gardenId);
    }

    public List<User> findAllUsersByGardenId(int gardenId) {
        return userRepository.findAllBygarden_gardenId(gardenId);
    }
}