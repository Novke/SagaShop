package rs.saga.obuka.sagashop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCmd {


    private String username;
    private String password;
    private String name;
    private String surname;

}
