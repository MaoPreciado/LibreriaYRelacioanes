package librery.demo.models.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

class BookDtoTest {

    //Seria bueno mockear Author para hacer la prueba aun mas aislada
    private BookDTO bookDTO;
    private BookDTO bookDTO2;
    private BookDTO bookDTO3;

    @BeforeEach
    void setup() {
        //given
         bookDTO = new BookDTO(20,"15d4w78", "Testing Book Dto", "Testing", 200,
                new AuthorDTO(8, "Testing Author",
                        "123789894", new Date(2024, Calendar.MARCH, 21),1));
         bookDTO2 = new BookDTO(21,"15d4w79", "Testing Book Dto 2", "Testing 2", 200,
                new AuthorDTO(8, "Testing Author",
                        "123789895", new Date(2024, Calendar.MARCH, 22),2));
        bookDTO3 = new BookDTO(20,"15d4w78", "Testing Book Dto", "Testing", 200,
                new AuthorDTO(8, "Testing Author",
                        "123789894", new Date(2024, Calendar.MARCH, 21),1));
    }

    @Test
    void getterSetterTest(){
        //getters
        assertEquals(20, bookDTO.getId());
        assertEquals("15d4w79", bookDTO2.getIsbn());
        assertEquals(200, bookDTO3.getPageNumber());

        //setters
        bookDTO.setId(22);
        bookDTO2.setTitle("El Hobbit");
        bookDTO3.setGenre("Aventura");

    }

    @Test
    void validateEqualsTest(){
        assertEquals(bookDTO.getId(), bookDTO3.getId());
        assertEquals(bookDTO.getIsbn(), bookDTO3.getIsbn());
        assertEquals(bookDTO.getAuthor().getIdentification(), bookDTO3.getAuthor().getIdentification());
    }

    //otras pruebas a realizar aunque no aplican para este ejemplo seria, verificar si los datos no estan en blanco,
    //verificar si no son nulos, verificar el hashcode, etc.
}
