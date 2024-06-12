package librery.demo.models.dtos;

import librery.demo.models.entities.AuthorEntity;

public class AuthorMapperUtility {
    public static AuthorDTO mapAuthorEntityToDto(AuthorEntity authorEntity) {
        return AuthorDTO.builder().id(authorEntity.getId())
                .name(authorEntity.getName())
                .identification(authorEntity.getIdentification())
                .birthday(authorEntity.getBirthday())
                .age(authorEntity.getAge()).build();
    }

    public static AuthorEntity mapAuthorDtoToEntity(AuthorDTO authorDTO) {
        return new AuthorEntity(authorDTO.getId(), authorDTO.getName(), authorDTO.getIdentification(),
                authorDTO.getBirthday(), authorDTO.getAge());
    }
}
