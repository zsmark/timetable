package hu.timetable.api.settlement.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "SETTLEMENT")
public class Settlement {

    @javax.persistence.Id
    @Column(name = "ID", precision = 22)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POPULATION")
    private Long population;

    @Override
    public String toString() {
        return name + ", " + population + " lakos";
    }
}
