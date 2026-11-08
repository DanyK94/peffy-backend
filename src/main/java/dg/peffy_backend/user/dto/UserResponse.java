package dg.peffy_backend.user.dto;
import java.time.LocalDateTime;

public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;

    public UserResponse(Integer id, String fistName, String lastName, String email, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = fistName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fistName) {
        this.firstName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
