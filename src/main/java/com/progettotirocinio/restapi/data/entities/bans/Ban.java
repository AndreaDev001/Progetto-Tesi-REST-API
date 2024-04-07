package com.progettotirocinio.restapi.data.entities.bans;

import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPath;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SpecificationPrefix
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "BANS",uniqueConstraints = {@UniqueConstraint(columnNames = {"BANNER_ID","BANNED_ID"})})
public class Ban extends GenericEntity implements OwnableEntity
{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BANNER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    protected User banner;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BANNED_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    protected User banned;

    @Column(name = "EXPIRATION_DATE",nullable = false)
    @SpecificationOrderType
    protected LocalDate expirationDate;

    @Column(name = "EXPIRED",nullable = false)
    protected boolean expired;

    @Column(name = "TYPE",nullable = false)
    @Enumerated(EnumType.STRING)
    @SpecificationOrderType
    protected BanType type;

    @Column(name = "REASON",nullable = false)
    @Enumerated(EnumType.STRING)
    @SpecificationOrderType
    protected ReportReason reason;

    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 3,max = 20)
    @SpecificationOrderType
    protected String title;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @Length(min = 20,max = 200)
    @SpecificationOrderType
    protected String description;

    @Override
    public UUID getOwnerID() {
        return this.banner.getId();
    }
}
