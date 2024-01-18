package com.example.authservice.request;
import com.example.authservice.enitity.Auth;
import com.example.commonservice.config.ValidationConfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = ValidationConfig.USER_REQUIRED_MESSAGE)
    @NotEmpty(message = ValidationConfig.USER_RESPONSE_MESSAGE)
    private String username ;
    @NotBlank(message = ValidationConfig.PASSWORD_REQUIRED_MESSAGE)
    @NotEmpty(message = ValidationConfig.PASSWORD_RESPONSE_MESSAGE)
    private String password ;
    private Long deptId;
    @NotBlank(message = ValidationConfig.ROLE_REQUIRED_MESSAGE)
    @NotEmpty(message = ValidationConfig.ROLE_RESPONSE_MESSAGE)
    private String role;
    private Boolean status;
    public Auth toEntity(LocalDateTime credDt, LocalDateTime lastMd){
        return new Auth(null,this.username.toLowerCase(),this.password,this.deptId,role,null,status,credDt,lastMd);
    }

}
