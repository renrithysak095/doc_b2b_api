package com.example.authservice.request;

import com.example.commonservice.config.ValidationConfig;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"username","password"})
public class UserRequest {
    @NotBlank(message = ValidationConfig.USER_REQUIRED_MESSAGE)
    @NotEmpty(message = ValidationConfig.USER_RESPONSE_MESSAGE)
    private String username ;
    @NotBlank(message = ValidationConfig.PASSWORD_REQUIRED_MESSAGE)
    @NotEmpty(message = ValidationConfig.PASSWORD_RESPONSE_MESSAGE)
    private String password ;
}
