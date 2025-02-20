package org.agoncal.quarkus.starting.mapper;

import org.agoncal.quarkus.starting.dto.BookDTO;
import org.agoncal.quarkus.starting.entity.Book;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.JAKARTA_CDI;

@Mapper(componentModel = JAKARTA_CDI)
public interface BookMapper {
    BookDTO mapToBookDto(Book book);
}
