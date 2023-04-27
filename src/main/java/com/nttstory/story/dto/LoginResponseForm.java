package com.nttstory.story.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseForm {

    private String accessToken;
    private Long expirationTime;
    private Long issuedTime;

    private String id;

}
