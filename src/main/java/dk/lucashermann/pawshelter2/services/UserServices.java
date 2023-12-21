package dk.lucashermann.pawshelter2.services;

import dk.lucashermann.pawshelter2.dataTransferObjects.UserDTO;
import dk.lucashermann.pawshelter2.models.User;
import dk.lucashermann.pawshelter2.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(UserDTO user) {
        User entityUser = new User(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                randomSalt()
                );
        return userRepository.save(entityUser);
    }

    public User updateUserName(Long id, String name) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(name);
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with ID" + id + " not found");
    }

    public User updateUserEmail(Long id, String email) {
        Optional<User> UserOptional = userRepository.findById(id);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            user.setEmail(email);
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with ID" + id + " not found");

    }

    public User updateUserPassword(Long id, String inputPassword) {
        Optional<User> UserOptional = userRepository.findById(id);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            // updating the salt too just ot be nice
            String newSalt = randomSalt();
            user.setSalt(newSalt);
            user.setPassword(stringHasher(inputPassword, newSalt));
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with ID" + id + " not found");
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean verifyUser(Long id, String inputPassword) {
        Optional<User> UserOptional = userRepository.findById(id);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            return user.getPassword().equals(stringHasher(inputPassword, user.getSalt()));
        } else
            throw new EntityNotFoundException("User with ID" + id + " not found");
    }

    private String randomSalt() {
        // the salt is generated using bytes for full 256 coverage
        byte[] salts = new byte[16]; // 16 bytes makes for 3.4E38
        SecureRandom random = new SecureRandom();
        random.nextBytes(salts);
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : salts) {
            hexBuilder.append(String.format("%02X", b));
            // the %02X means 2 digit hex where 0 serves as padding
        }
        return hexBuilder.toString();
        // the salt is in hexadecimal String format for consistency
    }

    private String stringHasher(String input, String salt) {
        String saltedInput = input + salt;
        try {
            // making a hasher from java.security
            MessageDigest SHA256 = MessageDigest.getInstance("SHA-256");
            byte[] hashes = SHA256.digest(saltedInput.getBytes(StandardCharsets.UTF_8));
            // the resulting byte[] is converted to a String
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : hashes) {
                hexBuilder.append(String.format("%02X", b));
                // the %02X means 2 digit hex where 0 serves as padding
            }

            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
