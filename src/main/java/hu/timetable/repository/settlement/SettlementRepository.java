package hu.timetable.repository.settlement;

import hu.timetable.api.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Repository
@RepositoryRestResource(path = "settlement")
public interface SettlementRepository extends JpaRepository<Settlement,Integer>{
}
