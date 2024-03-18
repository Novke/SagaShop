package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProduct extends BaseEntity<Long>{

    @ManyToOne(optional = false)
    private Product product;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Category category;

}
