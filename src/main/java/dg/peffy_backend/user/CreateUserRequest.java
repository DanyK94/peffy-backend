package dg.peffy_backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "Username Required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 char")
    private String username;

    @NotBlank(message = "Email Required")
    @Email(message = "Email is not Valid")
     @Size(max = 100, message = "Email must not exceed 100 char")
    private String email;

    @NotBlank(message = "Password Required")
     @Size(min = 8, max = 255, message = "Password must be between 8 and 255 char")
    private String passw;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String email, String passw) {
        this.username = username;
        this.email = email;
        this.passw = passw;
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

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
}
