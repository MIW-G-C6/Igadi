package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AuthorizationHelperTest {

    private GardenUserService createGardenUserServiceMock() {
        return Mockito.mock(GardenUserService.class);
    }

    private UserService createUserServiceMock() {
        return Mockito.mock(UserService.class);
    }

    @Test
    void noOneIsAGardenManager() {
        // case: no one is garden manager of any garden.
        GardenUserService gardenUserService = createGardenUserServiceMock();
        UserService userService = createUserServiceMock();
        AuthorizationHelper authorizationHelper = new AuthorizationHelper(gardenUserService, userService);
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        when(gardenUserService.findAllGardenUsersByAll(anyInt(), anyInt(), anyString())).thenReturn(gardenUsers);
        assertFalse(authorizationHelper.isUserGardenManager(1,1));
        assertFalse(authorizationHelper.isUserGardenManager(2, 2));
    }

    @Test
    void oneGardenHasAManager() {
        // case: user 1 is garden manager of garden 1, but not of other gardens. No one else is garden manager of any garden.
        GardenUserService gardenUserService = createGardenUserServiceMock();
        UserService userService = createUserServiceMock();
        AuthorizationHelper authorizationHelper = new AuthorizationHelper(gardenUserService, userService);
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        gardenUsers.add(new GardenUserDTO());
        when(gardenUserService.findAllGardenUsersByAll(1, 1, UserRole.GARDEN_MANAGER)).thenReturn(gardenUsers);
        assertTrue(authorizationHelper.isUserGardenManager(1, 1));
        assertFalse(authorizationHelper.isUserGardenManager(1, 2));
        assertFalse(authorizationHelper.isUserGardenManager(2, 5));
    }

    @Test
    void noOneIsAGardenMember() {
        // case: no one is a member of any garden.
        GardenUserService gardenUserService = createGardenUserServiceMock();
        UserService userService = createUserServiceMock();
        AuthorizationHelper authorizationHelper = new AuthorizationHelper(gardenUserService, userService);
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        when(gardenUserService.findAllGardenUsersByUserIdAndGardenId(anyInt(), anyInt())).thenReturn(gardenUsers);
        assertFalse(authorizationHelper.isUserGardenMember(1, 1));
        assertFalse(authorizationHelper.isUserGardenMember(2, 2));
    }

    @Test
    void oneGardenHasAMember() {
        // case: user 1 is gardener of garden 1, but is not a member of other gardens. No one else is a member of any garden.
        GardenUserService gardenUserService = createGardenUserServiceMock();
        UserService userService = createUserServiceMock();
        AuthorizationHelper authorizationHelper = new AuthorizationHelper(gardenUserService, userService);
        List<GardenUserDTO> gardenUsers = new ArrayList<>();
        gardenUsers.add(new GardenUserDTO());
        when(gardenUserService.findAllGardenUsersByUserIdAndGardenId(1, 1)).thenReturn(gardenUsers);
        assertTrue(authorizationHelper.isUserGardenMember(1,1));
        assertFalse(authorizationHelper.isUserGardenMember(1, 2));
        assertFalse(authorizationHelper.isUserGardenMember(2, 5));
    }
}