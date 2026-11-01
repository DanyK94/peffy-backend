package dg.peffy_backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank  
    @Size(max = 128)
    String password,

    @NotBlank
    @Email 
    String email
) {
    @Override
    public String toString(){
        return "LoginRequest[email= " + email + ", password=***]";
    }
    
}
