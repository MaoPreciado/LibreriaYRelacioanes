package librery.demo.servicies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import librery.demo.models.dtos.BookDTO;
import librery.demo.models.dtos.BookMapperutility;
import librery.demo.models.entities.AuthorEntity;
import librery.demo.models.entities.BookEntity;
import librery.demo.repositories.BookRepository;
import librery.demo.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookServiceTest {

    @MockBean //dependencias que se debe simular
    private BookRepository bookRepository;

    @Autowired //servicio a inyectar
    private BookService bookService;

    private BookEntity bookEntity;

    @BeforeEach
    void setup() {
       bookEntity = new BookEntity(17, "85fg1q", "Fake Book Entity", "Faking", 200, new AuthorEntity(
                17, "John Doe", "1153200487", new Date(2024, Calendar.MARCH, 21),  1
        ));
    }

    @Test
    void insertBookEntityTest() {
        //Given
        given(bookRepository.findBookByIsbn(bookEntity.getIsbn())).willReturn(Optional.empty());
        given(bookRepository.save(bookEntity)).willReturn(bookEntity);
        /*Si el isbn existe returna un vacio, si eso no se cumple guarde el libro*/

        //When
        BookEntity bookSaved = bookService.insertBookEntity(bookEntity);
        //Then
        assertThat(bookSaved).isNotNull();
        assertThat(bookSaved).isEqualTo(bookEntity); //aserciones, aunque es preferible usar las de JUnit5 : assertEquals
    }


    @DisplayName("book insertion with throw exception")
    @Test
    void insertBookEntityWithThrowExceptionTest(){
        //Given
        given(bookRepository.findBookByIsbn(bookEntity.getIsbn())).willReturn(Optional.of(bookEntity));
        /*Si encontramos, retorna un libro para desencadenar un error a drede*/

        //When
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.insertBookEntity(bookEntity);
        }); //estamos provocando el error
        //Then
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @DisplayName("get books by pagination")
    @Test
    void getBookByPaginationTest(){
        //Given
        //creamos un segundo book (el atributo bookEntity general para toda la clase y el bookEntity1)
        BookEntity bookEntity1 = new BookEntity(16, "86fg1q", "Fake Book Entity 2", "Faking", 200, new AuthorEntity(
                18, "John Doe 2", "1153200488", new Date(2024, Calendar.MARCH, 21),  1
        ));

        //es un poco complejo porque debemos crear la lista y crear el page para poder hacer la simulacion
        Pageable pageable = PageRequest.of(2, 5);
        List<BookEntity> bookEntityList = List.of(bookEntity,bookEntity1);
        Page<BookEntity> page = new PageImpl<>(bookEntityList, pageable, bookEntityList.size());

        //when
        when(bookRepository.findAll(pageable)).thenReturn(page);

        //then
        // Llama al método que quieres probar
        Page<BookDTO> result = bookService.getAllBooksWithPagination();

        // Verifica que el método de repositorio se llamó con los parámetros correctos
        verify(bookRepository).findAll(pageable);
    }

    @DisplayName("Insert DTO Book")
    @Test
    void insertDtoBook (){
        //Given
        //Given es mas complejo pues puede configurar precondiciones mas especificas para el mock que when, pero when sirve bastante
        //cuando se quiere probar algo breve
        BookDTO bookDTO =  BookMapperutility.mapBookEntityToDto(bookEntity);
        //aqui establecemos lo que se supone es tarea del repository
        given(bookRepository.findBookByIsbn(bookDTO.getIsbn())).willReturn(Optional.empty());
        given(bookRepository.save(bookEntity)).willReturn(bookEntity);

        //when
        //una vez establecido el trabajo del reposuitorio, definimos el trabajo del servicio en si.
        String confirmationString = bookService.insertBook(bookDTO);

        assertNotNull(confirmationString);
        assertEquals("Book has been successfully added to the database", confirmationString);
    }

    @DisplayName("Get all books by authors testing method")
    @Test
    void getAllBooksByAuthorTesting() {
        Integer authorId = 1;

        when(bookRepository.findAllBooksByAuthorId(any(Integer.class))).thenReturn(List.of(bookEntity));

        Set<BookDTO> bookDTOS = bookService.getAllBooksByAuthor(authorId);

        assertNotNull(bookDTOS, "bookDTOS can not be null");
        assertEquals(1,bookDTOS.size());
    }

    @DisplayName("Get all books by genre testing method")
    @Test
    void getAllBooksByGenreTesting() {
        String string = "fake";

        when(bookRepository.findAllBooksByGenre(any(String.class))).thenReturn(List.of(bookEntity));

        Set<BookDTO> bookDTOS = bookService.getAllBooksByGenre(string);

        assertNotNull(bookDTOS, "bookDTOS can not be null");
        assertEquals(1,bookDTOS.size());
    }

    @DisplayName("Get all books by page number testing method")
    @Test
    void getAllBooksByPageNumberTesting() {
        Integer pageNumber = 100;

        when(bookRepository.findAllBooksByPageNumber(any(Integer.class))).thenReturn(List.of(bookEntity));

        Set<BookDTO> bookDTOS = bookService.getAllBooksByPageNumber(pageNumber);

        assertNotNull(bookDTOS, "bookDTOS can not be null");
        assertEquals(1,bookDTOS.size());
    }

    @DisplayName("update service testing")
    @Test
    void updateBook() throws SQLException {
        //given
        Integer bookId = 1;
        Optional<BookEntity> optionalValue = Optional.of(bookEntity);
        when(bookRepository.findById(any(Integer.class))).thenReturn(optionalValue);

        bookService.updateBook(bookId);

        // Then
        //se verifica que el metodo save clave en el update sea llamado
        verify(bookRepository).save(bookEntity);
    }//usamos bookEntity para evitar el riesgo de un nullPointedException

    @DisplayName("delete service testing")
    @Test
    void deleteBookTesting () {
        Integer BookId = 1;
        bookService.deleteBook(1);
        verify(bookRepository).deleteById(BookId);
    }
}
