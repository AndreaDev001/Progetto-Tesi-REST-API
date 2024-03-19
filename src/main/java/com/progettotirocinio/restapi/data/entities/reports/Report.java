package com.progettotirocinio.restapi.data.entities.reports;


import com.progettotirocinio.restapi.data.converters.TrimConverter;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationOrderType;
import com.progettotirocinio.restapi.data.dao.specifications.annotations.SpecificationPrefix;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "REPORTS")
@SpecificationPrefix
public class Report extends GenericEntity implements OwnableEntity
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REPORTER_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    protected User reporter;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REPORTED_ID",nullable = false,updatable = false)
    @SpecificationOrderType(allowDepth = true)
    protected User reported;

    @Column(name = "TITLE",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    protected String title;

    @Column(name = "DESCRIPTION",nullable = false)
    @Convert(converter = TrimConverter.class)
    @SpecificationOrderType
    protected String description;

    @Column(name = "REASON",nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    protected ReportReason reason;

    @Column(name = "TYPE",nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    protected ReportType type;

    @Override
    public UUID getOwnerID() {
        return this.reporter.getId();
    }
}
