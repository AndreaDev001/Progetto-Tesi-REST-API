package com.progettotirocinio.restapi.data.entities.images;


import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image extends GenericEntity implements OwnableEntity
{

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,updatable = false)
    protected ImageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "OWNER_TYPE",nullable = false,updatable = false)
    protected ImageOwnerType owner;

    @Column(name = "IMAGE",nullable = false)
    protected byte[] image;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "UPLOADER_ID",nullable = false)
    private User uploader;

    @Override
    public UUID getOwnerID() {
        return uploader.getId();
    }
}
