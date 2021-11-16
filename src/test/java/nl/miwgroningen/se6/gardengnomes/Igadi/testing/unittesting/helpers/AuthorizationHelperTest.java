package nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
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
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
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
        when(gardenUserService.findAllGardenUsersByAll(1, 1, UserRole.GARDEN_MANAGER)).thenReturn(gardenUsers);
    }

    @Test
    void isUserGardenManager() {
        assertTrue(authorizationHelper.isUserGardenManager(1, 1));
        assertFalse(authorizationHelper.isUserGardenManager(1, 2));
    }
}