package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity<Long>{

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private RoleName name;

    public Role(Long id){
        setId(id);
    }
}
