package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
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
        ArrayList<GardenUser> gardenUsers = new ArrayList<>();
        GardenUser gardenUser = new GardenUser();
        gardenUser.setGardenUserId(1);
        Garden garden = new Garden();
        garden.setGardenId(1);
        gardenUser.setGarden(garden);
        User user = new User();
        user.setUserId(1);
        gardenUser.setUser(user);
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