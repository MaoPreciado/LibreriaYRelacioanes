package librery.demo.controllers;

import librery.demo.models.dtos.BookDTO;
import librery.demo.models.entities.BookEntity;
import librery.demo.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/library/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping
    public ResponseEntity<String> insertBook(@RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.insertBook(bookDTO));
    }

    @PostMapping("/insertBookEntity")
    public ResponseEntity<BookEntity> insertBookEntity(@RequestBody BookEntity bookEntity){
        return ResponseEntity.ok(bookService.insertBookEntity(bookEntity));
    }

    @GetMapping("/withFixedPagination")
    public ResponseEntity<Page<BookDTO>> getAllBooksWithPagination(){
        Optional<Page<BookDTO>> optionalBookPage = Optional.of(bookService.getAllBooksWithPagination());
        return ResponseEntity.of(optionalBookPage);
    }

    @GetMapping("/getAllBooksByAuthor")
    public ResponseEntity< Set<BookDTO>> getAllBooksByAuthor(@RequestParam Integer authorId){
        return ResponseEntity.ok(bookService.getAllBooksByAuthor(authorId));
    }

    @GetMapping("/getAllBooksByGenre")
    public ResponseEntity< Set<BookDTO>> getAllBooksByGenre(@RequestParam String genre){
        return ResponseEntity.ok(bookService.getAllBooksByGenre(genre));
    }

    @GetMapping("/getAllBooksByPageNumber")
    public ResponseEntity< Set<BookDTO>> getAllBooksByPageNumber(@RequestParam Integer pageNumber){
        return ResponseEntity.ok(bookService.getAllBooksByPageNumber(pageNumber));
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@RequestParam Integer id) throws SQLException {
        return ResponseEntity.ok(bookService.updateBook(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestParam Integer id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
