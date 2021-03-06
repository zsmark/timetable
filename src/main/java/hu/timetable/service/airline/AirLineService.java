package hu.timetable.service.airline;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.repository.ariline.AirLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirLineService {

    @Autowired
    private AirLineRepository airLineRepository;


    public List<AirLine> findAll() {
        return airLineRepository.findAll();
    }

}
