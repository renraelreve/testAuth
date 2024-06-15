package sctp.ntu.booking_api.services;

import java.util.ArrayList;

import sctp.ntu.booking_api.entities.Customer;
import sctp.ntu.booking_api.entities.Interaction;

public interface UserService {
    
    User createUser(User user);

    User getUser(Integer uid);

    ArrayList<User> getAllUsers();

    User updateUser(Integer uid, User user);

    void deleteUserr(Integer uid);

    ArrayList<User> searchUser(String name);
}
