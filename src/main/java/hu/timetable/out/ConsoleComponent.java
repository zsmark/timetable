package hu.timetable.out;

import hu.timetable.service.flight.FlightService;
import hu.timetable.service.settlement.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class ConsoleComponent {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SettlementService settlementService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Legkisebb város: " + settlementService.findSmallestCity());
        joiner.add("Legnagyobb város: " + settlementService.findBiggestCity());
        joiner.add(" ");

        joiner.add("A legrövidebb út: ");
        joiner.add(" ");

        flightService.findShortestRouteBetweenCitiesByAirlines().forEach(routeDto -> joiner.add(routeDto.toString()));
        joiner.add(" ");

        joiner.add("Bármely légitársasággal a legrövidebb út: ");
        joiner.add(" ");

        joiner.add(flightService.findShortestRouteBetweenCities().toString());

        System.out.println(joiner);
    }

}
