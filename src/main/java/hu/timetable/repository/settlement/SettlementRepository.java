package hu.timetable.repository.settlement;

import hu.timetable.api.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Repository
public interface SettlementRepository extends JpaRepository<Settlement,Integer>{

    @Query(nativeQuery = true, value = "select * From SETTLEMENT order by POPULATION asc LIMIT 1")
    Settlement findSmallestCity();

    @Query(nativeQuery = true, value = "select * From SETTLEMENT order by POPULATION desc LIMIT 1")
    Settlement findBiggestCity();
}
