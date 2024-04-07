package com.progettotirocinio.restapi.data.dto.input;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest
{
    @NotNull
    @PositiveOrZero
    Integer page;

    @NotNull
    @PositiveOrZero
    @Max(20)
    Integer pageSize;

    public Pageable toPageRequest() {
        return PageRequest.of(page,pageSize);
    }

    @Override
    public String toString() {
        return "?page=" + page.toString() + "&" + "pageSize=" + pageSize.toString();
    }
}
