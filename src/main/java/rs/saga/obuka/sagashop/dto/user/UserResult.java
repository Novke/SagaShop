package rs.saga.obuka.sagashop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResult {

    private String username;
    private String name;
    private String surname;

}
