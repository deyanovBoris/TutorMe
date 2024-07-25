package bg.softuni.tutorme.entities.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    @NotNull(message = "{register_user_username_sizing}")
    @Size(min = 4, max = 20, message = "{register_user_username_sizing}")
    private String username;
    @NotNull(message = "{register_user_full_name_sizing}")
    @Size(min = 2, max = 50, message = "{register_user_full_name_sizing}")
    private String fullName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8)
    private String password;
    @NotEmpty
    @Size(min = 8)
    private String confirmPassword;

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegisterDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
