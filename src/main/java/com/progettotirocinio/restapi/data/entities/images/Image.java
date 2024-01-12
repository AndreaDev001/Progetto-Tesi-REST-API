package com.progettotirocinio.restapi.data.entities.images;


import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,updatable = false)
    protected ImageType type;

    @Column(name = "IMAGE",nullable = false,updatable = false)
    protected byte[] image;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    protected LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    protected LocalDate lastModifiedDate;
}
