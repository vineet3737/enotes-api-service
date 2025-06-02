package com.becoder.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer id;

//    @NotNull
//    @Min(value = 10)
//    @Max(value = 100)
    private String name;

//    @NotNull
//    @Min(value = 10)
//    @Max(value = 100)
    private String description;

//    @NotNull
    private Boolean isActive;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;


}
