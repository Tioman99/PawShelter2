package dk.lucashermann.pawshelter2.services;

import dk.lucashermann.pawshelter2.dataTransferObjects.UserDTO;
import dk.lucashermann.pawshelter2.models.User;
import dk.lucashermann.pawshelter2.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findByEmail(String email) {
        Optional<User> userOPtional = userRepository.findByEmail(email);
        if(userOPtional.isPresent()){
            return convertToDTO(userOPtional.get());
        } else {
            throw new EntityNotFoundException("User with Email " + email + " not found");
        }
    }

    public User addUser(UserDTO user) {
        String salt = randomSalt();
        @Valid User entityUser = new User(
        user.getUsername(),
        user.getEmail(),
        stringHasher(user.getPassword(), salt),
        salt
        );
        return userRepository.save(entityUser);
    }

    public User updateUserName(String email, String name) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(name);
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with Email " + email + " not found");
    }

    public User updateUserEmail(String oldemail, String email) {
        Optional<User> UserOptional = userRepository.findByEmail(oldemail);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            user.setEmail(email);
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with Email " + email + " not found");
    }

    public User updateUserPassword(String email, String inputPassword) {
        Optional<User> UserOptional = userRepository.findByEmail(email);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            // updating the salt just ot be nice
            String newSalt = randomSalt();
            user.setSalt(newSalt);
            user.setPassword(stringHasher(inputPassword, newSalt));
            return userRepository.save(user);
        } else
            throw new EntityNotFoundException("User with Email " + email + " not found");
    }

    public void removeUser(String email) {
        userRepository.deleteUserByEmail(email);
    }

    public boolean verifyUser(String email, String inputPassword) {
        Optional<User> UserOptional = userRepository.findByEmail(email);
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            String password = user.getPassword();
            String salt = user.getSalt();
            return password.equals(stringHasher(inputPassword, salt));
        } else
            throw new EntityNotFoundException("User with Email " + email + " not found");
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

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}
