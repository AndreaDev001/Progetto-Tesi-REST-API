package com.progettotirocinio.restapi.data.entities.reports;

import com.progettotirocinio.restapi.data.entities.Discussion;
import com.progettotirocinio.restapi.data.entities.GenericEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISCUSSION_REPORTS")
public class DiscussionReport extends Report
{
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DISCUSSION_ID",nullable = false,updatable = false)
    private Discussion discussion;
}
