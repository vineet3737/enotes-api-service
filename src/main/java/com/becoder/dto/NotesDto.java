package com.becoder.dto;

import com.becoder.entity.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesDto {

    private Integer id;

    private String title;

    private String description;

    private CategoryDto category;

    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;

    private FileDto fileDetails;

    private boolean isDeleted;
    private LocalDateTime deletedOn;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileDto{

        private Integer id;
        private String originalFileName;
        private String displayFileName;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto{

        private Integer id;
        private String name;
      }

}
