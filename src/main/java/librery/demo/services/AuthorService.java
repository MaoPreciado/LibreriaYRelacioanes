package librery.demo.services;

import librery.demo.models.dtos.AuthorDTO;
import librery.demo.models.dtos.AuthorMapperUtility;
import librery.demo.models.entities.AuthorEntity;
import librery.demo.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    public String insertAuthor (AuthorDTO authorDTO){
        List<AuthorEntity> authors = authorRepository.findAll();
        authors.forEach(authorEntity -> {
            if (authorEntity.getIdentification().equalsIgnoreCase(authorDTO.getIdentification())){
                throw new IllegalArgumentException("Author's identification already exists");
            }
        });
        authorRepository.save(AuthorMapperUtility.mapAuthorDtoToEntity(authorDTO));
        return "Author has been successfully added to the database";
    }

    public Page<AuthorDTO> getAllAuthorsWithPagination(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AuthorEntity> authorEntities = authorRepository.findAll(pageable);
        return authorEntities.map(authorEntity ->
                new AuthorDTO(authorEntity.getId(), authorEntity.getName(), authorEntity.getIdentification(),
                        authorEntity.getBirthday(), authorEntity.getAge()));
    }

    public String updateAuthor(Integer id) throws SQLException {

        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        if (authorEntity.isPresent()) {
            authorEntity.get().setIdentification("1684784581");
            authorRepository.save(authorEntity.get());
            return "Author with id: " + id + " updated.";
        }else {
            throw new SQLException();
        }
    }

    public String deleteAuthor(Integer id){
        authorRepository.deleteById(id);
        return "Author with id: " + id + " deleted.";
    }
}
