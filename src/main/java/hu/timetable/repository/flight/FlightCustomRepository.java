package hu.timetable.repository.flight;

import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BEAR on 2017. 06. 15..
 */
@Repository
public class FlightCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> findByCities(String departure, String destination){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        List<Predicate> predicateList = new ArrayList<>();
        Join<Flight,Settlement> departureItem = root.join("departure");
        Join<Flight,Settlement> destinationItem = root.join("destination");
        if(departure != null){
            predicateList.add(builder.like(builder.lower(departureItem.get("name")),"%" + departure.toLowerCase() + "%"));
        }
        if(destination != null){
            predicateList.add(builder.like(builder.lower(destinationItem.get("name")),"%" + destination.toLowerCase() + "%"));
        }

        Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);

        return em.createQuery(query.select(root).where(predicates)).getResultList();
    }
}
