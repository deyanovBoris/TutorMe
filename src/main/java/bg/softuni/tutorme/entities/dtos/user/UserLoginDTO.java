package bg.softuni.tutorme.entities.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserLoginDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
