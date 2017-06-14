package hu.timetable.api.settlement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Settlement extends AbstractEntity{

    @JsonIgnore
    @javax.persistence.Id
    @GenericGenerator(name = "SEQ_SETTLEMENT", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_SETTLEMENT"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SETTLEMENT")
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POPULATION")
    private Long population;
}
