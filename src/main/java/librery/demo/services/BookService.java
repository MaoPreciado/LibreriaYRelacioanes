package librery.demo.services;


import librery.demo.models.dtos.AuthorMapperUtility;
import librery.demo.models.dtos.BookDTO;
import librery.demo.models.dtos.BookMapperutility;
import librery.demo.models.entities.BookEntity;
import librery.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Integer TWO = 2;
    private static final Integer FIVE = 5;

    @Autowired
    private  BookRepository bookRepository;
/*  @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);  ---> una forma elegante de hacer el mocking
    }*/
    public String insertBook (BookDTO book){
        List<BookEntity> books = bookRepository.findAll();
        books.forEach(bookEntity -> {
            if (bookEntity.getIsbn().equalsIgnoreCase(book.getIsbn())){
                throw new IllegalArgumentException("ISBN already exists");
            }
        });
        bookRepository.save(BookMapperutility.mapBookDtoToEntity(book));
        return "Book has been successfully added to the database";
    }

    public BookEntity insertBookEntity(BookEntity bookEntity){
        Optional<BookEntity> optionalBookEntity = bookRepository.findBookByIsbn(bookEntity.getIsbn());
        if (optionalBookEntity.isPresent()){
            throw new IllegalArgumentException("ISBN already exists");
        }
        return bookRepository.save(bookEntity);
    }
    public Page<BookDTO> getAllBooksWithPagination(){
        Pageable pageable = PageRequest.of(TWO,FIVE);
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);
        return  bookEntities.map(bookEntity -> BookDTO.builder()
                .id(bookEntity.getId())
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .genre(bookEntity.getGenre())
                .pageNumber(bookEntity.getPageNumber())
                .author(AuthorMapperUtility.mapAuthorEntityToDto(bookEntity.getAuthor()))
                .build());
    }

    public Set<BookDTO> getAllBooksByAuthor(Integer authorId){
        return bookRepository.findAllBooksByAuthorId(authorId).stream()
                .map(BookMapperutility::mapBookEntityToDto).collect(Collectors.toSet());
    }

    public Set<BookDTO> getAllBooksByGenre(String genre){
        return bookRepository.findAllBooksByGenre(genre).stream()
                .map(BookMapperutility::mapBookEntityToDto).collect(Collectors.toSet());
    }

    public Set<BookDTO> getAllBooksByPageNumber(Integer pageNumber){
        return bookRepository.findAllBooksByPageNumber(pageNumber).stream()
                .map(BookMapperutility::mapBookEntityToDto).collect(Collectors.toSet());
    }

    public String updateBook(Integer id) throws SQLException {

        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if (bookEntity.isPresent()) {
            bookEntity.get().setPageNumber(200);
            bookRepository.save(bookEntity.get());
            return "Book with id: " + id + " updated.";
        }else {
            throw new SQLException();
        }
    }

    public String deleteBook(Integer id){
        bookRepository.deleteById(id);
        return "Book with id: " + id + " deleted.";
    }
}
