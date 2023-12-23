package dk.lucashermann.pawshelter2.controllers;

import dk.lucashermann.pawshelter2.dataTransferObjects.UserDTO;
import dk.lucashermann.pawshelter2.services.UserServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // I will note that this is NOT secure as it is vulnerable to spoofing

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO user = userService.findByEmail(email);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity<Boolean> verifyUserPassword(@PathVariable String email, @RequestParam String password) {
        try {
            boolean isPasswordValid = userService.verifyUser(email, password);
            return ResponseEntity.ok(isPasswordValid);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserDTO user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/newusername/{email}")
    public ResponseEntity<Void> updateUserName(@PathVariable String email, @RequestParam String name) {
        try {
            userService.updateUserName(email, name);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/newemail/{email}")
    public ResponseEntity<Void> updateUserEmail(@PathVariable String email, @RequestParam String newemail) {
        try {
            userService.updateUserEmail(email, newemail);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/newpassword/{email}")
    public ResponseEntity<Void> updateUserPassword(@PathVariable String email, @RequestParam String password) {
        try {
            userService.updateUserPassword(email, password);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> removeUser(@PathVariable String email) {
        try {
            userService.removeUser(email);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
