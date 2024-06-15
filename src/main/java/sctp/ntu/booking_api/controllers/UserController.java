package sctp.ntu.booking_api.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sctp.ntu.booking_api.entities.User;
import sctp.ntu.booking_api.entities.Booking;
import sctp.ntu.booking_api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    // @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<User>> searcUsers(@RequestParam String name) {
        ArrayList<User> foundUsers = userService.searchUsers(name);
        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        
        // if(bindingResult.hasErrors()) {
        //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // }
        
        User newUser = UserService.createUser(customer);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // READ (GET ALL)
    @GetMapping("")
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User foundUser = UserService.getUser(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateCustomer(@PathVariable Integer id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        UserService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
