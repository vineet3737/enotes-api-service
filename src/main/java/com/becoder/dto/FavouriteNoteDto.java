package com.becoder.dto;

import com.becoder.entity.Notes;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteNoteDto {

    private Integer id;

    private Notes note;

    private Integer userId;
}
