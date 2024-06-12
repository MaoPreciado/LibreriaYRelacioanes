package librery.demo.models.dtos;

import librery.demo.models.entities.BookEntity;

public class BookMapperutility {

    public static BookDTO mapBookEntityToDto(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(bookEntity.getId())
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .genre(bookEntity.getGenre())
                .pageNumber(bookEntity.getPageNumber())
                .author(AuthorMapperUtility.mapAuthorEntityToDto(bookEntity.getAuthor()))
                .build();
    }

    public static BookEntity mapBookDtoToEntity(BookDTO bookDTO) {
        return new BookEntity(bookDTO.getId(), bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getGenre(),
                bookDTO.getPageNumber(), AuthorMapperUtility.mapAuthorDtoToEntity(bookDTO.getAuthor()));
    }
}
