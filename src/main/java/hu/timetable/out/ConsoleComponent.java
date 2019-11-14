package hu.timetable.out;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.service.airline.AirLineService;
import hu.timetable.service.flight.FlightService;
import hu.timetable.service.settlement.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

@Component
public class ConsoleComponent {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private AirLineService airLineService;

    @EventListener(ApplicationReadyEvent.class)
    public void initConsole() {
        String line;

        printFunctions();

        while ((line = getNextLine()) != null && !line.equals("exit")) {
            switch (line) {
                case "1":
                    printSmallestBiggestCities();
                    break;
                case "2":
                    printAllCities();
                    break;
                case "3":
                    printAllAirlines();
                    break;
                case "4":
                    printAllFlightsByAirlines();
                    break;
                case "5":
                    printAllFlightsByCities();
                    break;
                default:
                    System.out.println("A megadott érték nem megfelelő!");
            }
            System.out.println(" ");
            printFunctions();
        }

    }

    private void printAllFlightsByCities() {
        String cities;
        System.out.println("Kérem adja meg a városok nevét vesszővel elválasztva: ");
        while ((cities = getNextLine()) != null && !cities.equals("exit")) {
            String[] cityArray = cities.split(",");
            List<Flight> result = null;
            if (cityArray.length == 2) {
                result = flightService.findByCities(cityArray[0], cityArray[1]);
            } else if (cityArray.length == 1) {
                result = flightService.findByCities(cityArray[0], null);
            } else {
                System.out.println("Nem adott meg helyes értékeket!");
            }

            if (result != null && !result.isEmpty()) {
                result.forEach(flight -> System.out.println(flight.getTostringWithAirlineName()));
                break;
            } else {
                System.out.println("Nincsen a megadott értékeknek megfelelő találat! Adjon meg újakat vagy írja be az exit szót!");
            }
        }
    }

    private String getNextLine() {
        Console console = System.console();
        if (console != null)
            return console.readLine();

        Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        return scanner.nextLine();
    }

    private void printFunctions() {
        System.out.println("Kérem a következő lehetőségek közül válasszon: ");
        System.out.println("1. A legkisebb illetve legnyagyobb városok közötti útvonalak");
        System.out.println("2. Összes elérhető város listázása");
        System.out.println("3. Összes légitársaság listázása");
        System.out.println("4. Adott légitársaság összes járatának listázása");
        System.out.println("5. Összes járat listázása adott városból a célállomásra");
        System.out.println("exit - kilépés");

        System.out.println(" ");
        System.out.println("Válasszon funkciót!");
        System.out.println(" ");
    }

    private void printAllFlightsByAirlines() {
        Scanner scanner = new Scanner(System.in);
        String airline;
        System.out.println("Kérem adja meg a légitársaság nevét: ");
        while ((airline = scanner.nextLine()) != null && !airline.equals("exit")) {
            List<Flight> flightsByName = flightService.findFlightsByName(airline);
            if (flightsByName == null || flightsByName.isEmpty()) {
                System.out.println("Nincs ilyen nevű légitársaság vagy nincsenek járatai! Válasszon másikat vagy írja be az exit szót!");
            } else {
                flightsByName.forEach(System.out::println);
                break;
            }
        }
    }

    private void printAllAirlines() {
        List<AirLine> result = airLineService.findAll();
        result.forEach(System.out::println);
    }

    private void printAllCities() {
        List<Settlement> result = settlementService.findAll();
        result.forEach(System.out::println);
    }

    private void printSmallestBiggestCities() {
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
