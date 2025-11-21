package com.example.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("tutorial")
public class Tutorial {
    @Id
    private Long id;
    private String title;
    private String description;
    private Boolean published;
}
