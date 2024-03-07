package rs.saga.obuka.sagashop.domain;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;

/**
 * @author: Ana Dedović
 * Date: 24.06.2021.
 **/
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Category extends BaseEntity<Long> {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column
    private String description;
}
