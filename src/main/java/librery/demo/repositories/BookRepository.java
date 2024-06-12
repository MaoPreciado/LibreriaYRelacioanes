package librery.demo.repositories;

import librery.demo.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {


     Optional<BookEntity> findBookByIsbn(String isbn);
     List<BookEntity> findAllBooksByAuthorId(Integer authorId);
     List<BookEntity> findAllBooksByGenre(String genre);
     List<BookEntity> findAllBooksByPageNumber(Integer pages);
}
