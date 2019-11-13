package hu.timetable.repository.settlement;

import hu.timetable.api.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement,Integer>{

    @Query(nativeQuery = true, value = "select * From SETTLEMENT order by POPULATION asc LIMIT 1")
    Settlement findSmallestCity();

    @Query(nativeQuery = true, value = "select * From SETTLEMENT order by POPULATION desc LIMIT 1")
    Settlement findBiggestCity();
}
