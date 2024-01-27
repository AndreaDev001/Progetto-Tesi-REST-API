package com.progettotirocinio.restapi.data.dto.output.reports;

import com.progettotirocinio.restapi.data.dto.output.refs.PollRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "content")
public class PollReportDto extends ReportDto {
    private PollRef poll;
}
