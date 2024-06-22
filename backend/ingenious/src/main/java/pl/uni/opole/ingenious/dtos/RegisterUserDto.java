package pl.uni.opole.ingenious.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class RegisterUserDto {

    @NotNull(message = "email shouldn't be empty")
    @NotEmpty(message = "email shouldn't be empty")
    private String email;

    @Length(min = 4, message = "minimum password length is 4")
    private String password;

    @NotNull(message = "email shouldn't be empty")
    @NotEmpty(message = "email shouldn't be empty")
    private String fullName;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}