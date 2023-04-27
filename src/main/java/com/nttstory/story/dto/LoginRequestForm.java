package com.nttstory.story.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestForm {
    @NotNull
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String password;
}
