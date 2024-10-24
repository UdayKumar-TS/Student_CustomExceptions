package StudentBackend.Student.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Login")
public class LoginDto {


    private String email;
    private String password;
}
