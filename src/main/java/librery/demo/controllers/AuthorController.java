package librery.demo.controllers;

import librery.demo.models.dtos.AuthorDTO;
import librery.demo.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/library/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<String> insertAuthor (@RequestBody AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.insertAuthor(authorDTO));
    }

    @GetMapping("/getAuthorsWithDynamicPagination")
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorsWithPagination
            (@RequestParam Integer pageNumber, @RequestParam Integer size){
        Page<AuthorDTO> authorDTOPage = authorService.getAllAuthorsWithPagination(pageNumber,size);
        return ResponseEntity.ok(authorDTOPage);
    }

    @PutMapping
    public ResponseEntity<String> updateAuthor(@RequestParam Integer id) throws SQLException {
        return ResponseEntity.ok(authorService.updateAuthor(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAuthor(@RequestParam Integer id){
        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }
}
