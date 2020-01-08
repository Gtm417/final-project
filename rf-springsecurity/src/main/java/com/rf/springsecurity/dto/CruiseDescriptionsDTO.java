package com.rf.springsecurity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CruiseDescriptionsDTO {
    private String description_eng;
    private String description_ru;
}
