package librery.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import librery.demo.models.dtos.AuthorDTO;
import librery.demo.models.dtos.BookDTO;

import librery.demo.models.entities.AuthorEntity;
import librery.demo.models.entities.BookEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void saveBookRepositorytest(){
        //Given - dado (precondición)
        BookEntity bookEntity = new BookEntity(17, "85fg1q", "Fake Book Entity", "Faking", 200, new AuthorEntity(
                17, "John Doe", "1153200487", new Date(2024, Calendar.MARCH, 21),  1
        ));

        BookEntity savedBook = bookRepository.save(bookEntity);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    static BookDTO getBookDtoForRequest(){
        return BookDTO.builder()
                .id(16)
                .isbn("15AB4C")
                .title("Fake Book For Testing")
                .genre("Faking")
                .pageNumber(200)
                .author(AuthorDTO.builder()
                        .id(16)
                        .name("John Doe")
                        .identification("11579020")
                        .birthday(new Date(2024, Calendar.MARCH,21))
                        .age(1).build())
                .build();
    }


}


