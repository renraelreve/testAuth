package sctp.ntu.booking_api.serviceImpls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sctp.ntu.booking_api.entities.User;
import sctp.ntu.booking_api.exceptions.UserNotFoundException;
import sctp.ntu.booking_api.repositories.UserRepository;
import sctp.ntu.booking_api.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    private UserRepository userRepository;

    // @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<User> searchUser(String name) {
        List<User> foundUsers = userRepository.findByName(name);
        return (ArrayList<User>) foundUsers;
    }
    public User findOneUser(String name) {
        User foundUser = userRepository.findOneByName(name);
        return foundUser;
    }

    @Override
    public User createUser(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public User getUser(Integer uid) {
        // Optional<Customer> optionalCustomer = customerRepository.findById(id);
        // if(optionalCustomer.isPresent()) {
        //     Customer foundCustomer = optionalCustomer.get();
        //     return foundCustomer;
        // }
        // throw new CustomerNotFoundException(id);
        return userRepository.findById(uid).orElseThrow(()-> new UserNotFoundException(uid));
    }

    @Override
    public ArrayList<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    @Override
    public User updateUser(Integer uid, User user) {
        // retrieve the customer from the database
        // [Activity 1 - Refactor code]
        User userToUpdate = userRepository.findById(uid).orElseThrow(()-> new UserNotFoundException(uid));
        // update the customer retrieved from the database
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        // save the updated customer back to the database
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Integer uid) {
        userRepository.deleteById(uid);
    }

}
