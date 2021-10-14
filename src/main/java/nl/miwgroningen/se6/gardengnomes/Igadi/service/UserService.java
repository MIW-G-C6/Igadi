package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
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
        userDTO.setUserPassword(user.getUserPassword());
        userDTO.setUserRole(user.getUserRole());
        return userDTO;
    }

    public UserDTO getUserById(int userId) {
        User user = userRepository.getById(userId);
        return convertToUserDTO(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean checkIfUserNameExists(String userName) {
        boolean userNameIsInDatabase = false;
        List<UserDTO> users = this.getAllUsers();
        for(UserDTO user : users) {
            if(user.getUserName().equals(userName)) {
                userNameIsInDatabase = true;
                break;
            }
        }
        return userNameIsInDatabase;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(s).orElseThrow(
                () -> new UsernameNotFoundException("Username " + s + " was not found!")
        );
    }
}
