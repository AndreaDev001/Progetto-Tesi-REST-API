package com.progettotirocinio.restapi.data.entities.images;


import com.progettotirocinio.restapi.data.entities.Board;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage extends Image {
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BOARD_ID",nullable = false,updatable = false)
    private Board board;
}
