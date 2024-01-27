package com.progettotirocinio.restapi.data.entities.reports;

import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "DISCUSSION_REPORTS")
public class DiscussionReport extends Report
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DISCUSSION_ID",nullable = false,updatable = false)
    private Discussion discussion;
}
