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
        //Setup
        List<User> users = new ArrayList<>();
        users.add(new User());
        //Mock
        when(userRepository.findByName(anyString())).thenReturn(users);
        //Execute
        List<User> foundUsers = userServiceImpl.searchUser("John");
        //Assert
        assertEquals(1, foundUsers.size());
    }

    @Test
    public void testFindOneUser_Valid() {
        //Setup
        User user = new User();
        //Mock
        when(userRepository.findOneByName(anyString())).thenReturn(user);
        //Execute
        User foundUser = userServiceImpl.findOneUser("John");
        //Assert
        assertNotNull(foundUser);
    }

    @Test
    public void testCreateUser_Valid() {
        //Setup
        User user = new User();
        //Mock
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Execute
        User createdUser = userServiceImpl.createUser(user);
        //Assert
        assertNotNull(createdUser);
    }

    @Test
    public void testGetUser_Valid() {
        //Setup
        User user = new User();
        //Mock
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        //Execute
        User foundUser = userServiceImpl.getUser(1);
        //Assert
        assertNotNull(foundUser);
    }

    @Test
    public void testGetUser_NotFound() {
        //Mock
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        //Execute & Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser(1));
    }

    @Test
    public void testGetAllUsers() {
        //Setup
        List<User> users = new ArrayList<>();
        users.add(new User());
        //Mock
        when(userRepository.findAll()).thenReturn(users);
        //Execute
        List<User> allUsers = userServiceImpl.getAllUsers();
        //Assert
        assertEquals(1, allUsers.size());
    }

    @Test
    public void testUpdateUser_Valid() {
        //Setup
        User user = new User();
        user.setName("UpdatedName");
        //Mock
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Execute
        User updatedUser = userServiceImpl.updateUser(1, user);
        //Assert
        assertEquals("UpdatedName", updatedUser.getName());
    }

    @Test
    public void testDeleteUser_Valid() {
        //Setup
        User user = new User();
        //Mock
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(anyInt());
        //Execute
        userServiceImpl.deleteUser(1);
        //Verify
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}