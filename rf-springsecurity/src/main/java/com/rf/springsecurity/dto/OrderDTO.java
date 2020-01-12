package com.rf.springsecurity.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    @NotNull
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "The name should contain only Latin characters and begin with a upper case character"
    )
    private String firstName;
    @NotNull
    @Pattern(
            regexp = "^[A-Z][a-z]{1,20}$",
            message = "The name should contain only Latin characters and begin with a upper case character"
    )
    private String secondName;
    private String cruiseName;
    @NotNull
    private long ticket_id;
    private List<String> excursionNames;
}
