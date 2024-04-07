package com.progettotirocinio.restapi.data.dto.output.bans;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
import com.progettotirocinio.restapi.data.entities.enums.BanType;
import com.progettotirocinio.restapi.data.entities.enums.ReportReason;
import com.progettotirocinio.restapi.data.entities.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class BanDto extends GenericOutput<BanDto> {
    protected String title;
    protected String description;
    protected ReportReason reason;
    protected BanType type;
    protected LocalDate expirationDate;
    protected boolean expired;
    protected UserRef banner;
    protected UserRef banned;
}
