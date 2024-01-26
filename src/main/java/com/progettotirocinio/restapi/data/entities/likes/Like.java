package com.progettotirocinio.restapi.data.entities.likes;


import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.LikeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "LIKES")
public class Like extends GenericEntity
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    protected User user;

    @Column(name = "TYPE",nullable = false,updatable = false)
    protected LikeType type;
}
