package rs.saga.obuka.sagashop.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}
