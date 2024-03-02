package com.progettotirocinio.restapi.data.entities.images;


import com.progettotirocinio.restapi.data.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserImage extends Image
{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;
}
