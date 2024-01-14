package com.progettotirocinio.restapi.data.entities;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.entities.enums.BoardInviteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BOARD_INVITES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardInvite
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "TEXT",nullable = false,updatable = false)
    @Convert(converter = TrimConverter.class)
    private String text;

    @Column(name = "STATUS",nullable = false)
    private BoardInviteStatus status;

    @Column(name = "EXPIRATION_DATE",nullable = false,updatable = false)
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "PUBLISHER_ID",nullable = false,updatable = false)
    private User publisher;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;

    @CreatedDate
    @Column(name = "CREATED_DATE",nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",nullable = false,updatable = false)
    private LocalDate lastModifiedDate;
}
