package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.UserRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.UserConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userConverter::convertToUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(int userId) {
        User user = userRepository.getById(userId);
        return userConverter.convertToUserDTO(user);
    }

    public void saveUser(UserDTO userDTO) {
        User user = userConverter.convertFromUserDTO(userDTO);
        userRepository.save(user);
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

    public UserDTO findUserByUsername(String username) {
        return userConverter.convertToUserDTO(userRepository.findUserByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("Name " + username + " was not found!")
        ));
    }

    public List<UserDTO> findByNameContains(String keyword){
        List<User> foundList = userRepository.findByUserNameContains(keyword);
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : foundList) {
            userDTOs.add(userConverter.convertToUserDTO(user));
        }
        return userDTOs;
    }
}