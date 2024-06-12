package librery.demo.models.dtos;

import librery.demo.models.entities.AuthorEntity;
import librery.demo.models.entities.BookEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

class BookMapperUtilityTest {

    @Test
    void testMapABookEntityToDto() {
        //Given
        BookEntity bookEntity = new BookEntity(20,"15d4w78", "Testing Book Dto", "Testing", 200,
                new AuthorEntity(8, "Testing Author",
                        "123789894", new Date(2024, Calendar.MARCH, 21),1));

        //When
        BookDTO bookDTO = BookMapperutility.mapBookEntityToDto(bookEntity);

        //Then
        Assertions.assertEquals(bookDTO.getId(), bookEntity.getId());
        Assertions.assertEquals(bookDTO.getIsbn(), bookEntity.getIsbn());
        Assertions.assertEquals(bookDTO.getTitle(), bookEntity.getTitle());
        Assertions.assertEquals(bookDTO.getPageNumber(),bookEntity.getPageNumber());
        Assertions.assertEquals(bookDTO.getAuthor().getIdentification(),bookEntity.getAuthor().getIdentification());
    }

    @Test
    void testMapBookDtoToEntity() {
        //Given
        BookDTO bookDTO = new BookDTO(20, "15d4w78", "Testing Book Dto", "Testing", 200,
                new AuthorDTO(8, "Testing Author",
                        "123789894", new Date(2024, Calendar.MARCH, 21), 1));

        //When
        BookEntity bookEntity = BookMapperutility.mapBookDtoToEntity(bookDTO);

        //Then
        Assertions.assertEquals(bookEntity.getId(), bookDTO.getId());
        Assertions.assertEquals(bookEntity.getIsbn(), bookDTO.getIsbn());
        Assertions.assertEquals(bookEntity.getTitle(), bookDTO.getTitle());
        Assertions.assertEquals(bookEntity.getPageNumber(),bookDTO.getPageNumber());
        Assertions.assertEquals(bookEntity.getAuthor().getIdentification(),bookDTO.getAuthor().getIdentification());
    }
}
