package hu.timetable.api.settlement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Getter
@Setter
@Entity
@Table(name = "SETTLEMENT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Settlement extends AbstractEntity{

    @JsonIgnore
    @javax.persistence.Id
//    @GenericGenerator(name = "SEQ_SETTLEMENT", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_SETTLEMENT"),
//                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SETTLEMENT")
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @JsonProperty("Név")
    @Column(name = "NAME")
    private String name;

    @JsonIgnore
//    @JsonProperty("Lakosság")
    @Column(name = "POPULATION")
    private Long population;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settlement that = (Settlement) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return population != null ? population.equals(that.population) : that.population == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        return result;
    }
}
