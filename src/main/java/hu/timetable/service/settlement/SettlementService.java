package hu.timetable.service.settlement;

import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.repository.settlement.SettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    public Settlement findSmallestCity(){
        return settlementRepository.findSmallestCity();
    }

    public Settlement findBiggestCity(){
        return settlementRepository.findBiggestCity();
    }

    public List<Settlement> findAll() {
        return settlementRepository.findAll();
    }
}
