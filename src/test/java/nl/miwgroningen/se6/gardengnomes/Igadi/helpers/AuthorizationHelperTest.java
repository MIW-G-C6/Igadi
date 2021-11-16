package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationHelperTest {

    @Mock
    private GardenUserService gardenUserService;
    private AuthorizationHelper authorizationHelper;

    @BeforeEach
    void setUp() {
        authorizationHelper = new AuthorizationHelper(gardenUserService);
    }

    @Test
    void isUserGardenManager() {
        // case: no one is garden manager of any garden.
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        when(gardenUserService.findAllGardenUsersByAll(1, 1, UserRole.GARDEN_MANAGER)).thenReturn(gardenUsers);
        assertFalse(authorizationHelper.isUserGardenManager(1,1));
        assertFalse(authorizationHelper.isUserGardenManager(2, 2)); // why does this return false?

        // case: user 1 is garden manager of garden 1, but not of other gardens. No one else is garden manager of any garden.
        GardenUserDTO gardenUser = new GardenUserDTO();
        gardenUser.setGardenUserId(1);
        GardenDTO garden = new GardenDTO();
        garden.setGardenId(1);
        gardenUser.setGardenDTO(garden);
        UserDTO user = new UserDTO();
        user.setUserId(1);
        gardenUser.setUserDTO(user);
        gardenUser.setRole(UserRole.GARDEN_MANAGER);
        gardenUsers.add(gardenUser);
        assertTrue(authorizationHelper.isUserGardenManager(1, 1));
        assertFalse(authorizationHelper.isUserGardenManager(1, 2));
        assertFalse(authorizationHelper.isUserGardenManager(2, 5));
    }

    @Test
    void isUserGardenMember() {
        // case: no one is a member of any garden.
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        when(gardenUserService.findAllGardenUsersByUserIdAndGardenId(1, 1)).thenReturn(gardenUsers);
        assertFalse(authorizationHelper.isUserGardenMember(1,1));

        // case: user 1 is gardener of garden 1, but is not a member of other gardens. No one else is a member of any garden.
        GardenUserDTO gardenUser = new GardenUserDTO();
        gardenUser.setGardenUserId(1);
        GardenDTO garden = new GardenDTO();
        garden.setGardenId(1);
        gardenUser.setGardenDTO(garden);
        UserDTO user = new UserDTO();
        user.setUserId(1);
        gardenUser.setUserDTO(user);
        gardenUser.setRole(UserRole.GARDENER);
        gardenUsers.add(gardenUser);
        assertTrue(authorizationHelper.isUserGardenMember(1,1));
        assertFalse(authorizationHelper.isUserGardenMember(1, 2));
        assertFalse(authorizationHelper.isUserGardenManager(2, 5));
    }
}