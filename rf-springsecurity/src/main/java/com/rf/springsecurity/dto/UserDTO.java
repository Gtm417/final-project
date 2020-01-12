package com.rf.springsecurity.dto;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    @Pattern(regexp = "([a-zA-Z \\d -_]).{6,16}", message = "more than 6 Latin characters. Access characters \"-_\" ")
    private String login;
    @Pattern(regexp = "([a-zA-Z \\d -_]).{6,16}", message = "more than 6 Latin characters. Access characters \"-_\" ")
    private String password;
}
