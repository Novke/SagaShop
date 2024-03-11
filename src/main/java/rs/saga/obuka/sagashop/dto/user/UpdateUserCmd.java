package rs.saga.obuka.sagashop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCmd {
    private Long id;
    private String username;
    private String password;
}
