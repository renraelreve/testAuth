package sctp.ntu.booking_api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sctp.ntu.booking_api.entities.User;
import sctp.ntu.booking_api.exceptions.UserNotFoundException;
import sctp.ntu.booking_api.repositories.UserRepository;
import sctp.ntu.booking_api.serviceImpls.UserServiceImpl;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchUser_Valid() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findByName(anyString())).thenReturn(users);
        List<User> foundUsers = userServiceImpl.searchUser("John");
        assertEquals(1, foundUsers.size());
    }

    @Test
    public void testFindOneUser_Valid() {
        User user = new User();
        when(userRepository.findOneByName(anyString())).thenReturn(user);
        User foundUser = userServiceImpl.findOneUser("John");
        assertNotNull(foundUser);
    }

    @Test
    public void testCreateUser_Valid() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userServiceImpl.createUser(user);
        assertNotNull(createdUser);
    }

    @Test
    public void testGetUser_Valid() {
        User user = new User();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        User foundUser = userServiceImpl.getUser(1);
        assertNotNull(foundUser);
    }

    @Test
    public void testGetUser_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser(1));
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userServiceImpl.getAllUsers();
        assertEquals(1, allUsers.size());
    }

    @Test
    public void testUpdateUser_Valid() {
        User user = new User();
        user.setName("UpdatedName");
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userServiceImpl.updateUser(1, user);
        assertEquals("UpdatedName", updatedUser.getName());
    }

    @Test
    public void testDeleteUser_Valid() {
        doNothing().when(userRepository).deleteById(anyInt());
        userServiceImpl.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}