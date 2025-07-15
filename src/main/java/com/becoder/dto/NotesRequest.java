package com.becoder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotesRequest {

    private String title;

    private String description;

    private NotesDto.CategoryDto category;
}
