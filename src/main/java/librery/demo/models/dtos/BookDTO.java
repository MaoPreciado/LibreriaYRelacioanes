package librery.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Integer id;
    private String isbn;
    private String title;
    private String genre;
    private Integer pageNumber;
    private AuthorDTO author;
}
