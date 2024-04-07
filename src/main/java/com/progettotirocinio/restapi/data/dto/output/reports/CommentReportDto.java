package com.progettotirocinio.restapi.data.dto.output.reports;

import com.progettotirocinio.restapi.data.dto.output.refs.CommentRef;
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
public class CommentReportDto extends ReportDto
{
    private CommentRef comment;
}
