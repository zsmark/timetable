package hu.timetable.api.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Getter
@Setter
@MappedSuperclass
public class AbstractEntity implements Serializable {

    @JsonIgnore
    @Version
    @Column(name = "VERSION")
    private int version;
}
