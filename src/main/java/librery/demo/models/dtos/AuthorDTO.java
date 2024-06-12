package librery.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AuthorDTO {

    private Integer id;
    private String name;
    private String identification;
    private Date birthday;
    private Integer age;
}
