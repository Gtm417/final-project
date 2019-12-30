package com.rf.springsecurity.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BalanceUserDTO {
    String login;
    long balance;
}
