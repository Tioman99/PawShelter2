package dk.lucashermann.pawshelter2.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private String username;
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    @JsonIgnore
    // excluding password from GET .../users/all and such
    private String password;

    public UserDTO() {
        // no-arg constructor for serialization
    }

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
