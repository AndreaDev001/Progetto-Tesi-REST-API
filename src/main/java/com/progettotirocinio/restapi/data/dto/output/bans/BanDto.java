package com.progettotirocinio.restapi.data.dto.output.bans;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.refs.UserRef;
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
    protected LocalDate expirationDate;
    protected boolean expired;
    protected UserRef banner;
    protected UserRef banned;
}