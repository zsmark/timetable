package hu.timetable.repository.flight;

import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightCustomRepositoryImpl implements FlightCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Flight> findByCities(String departure, String destination) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        List<Predicate> predicateList = new ArrayList<>();
        Join<Flight, Settlement> departureItem = root.join("departure");
        Join<Flight, Settlement> destinationItem = root.join("destination");
        if (departure != null) {
            predicateList.add(builder.equal(builder.lower(departureItem.get("name")), departure.toLowerCase().trim()));
        }
        if (destination != null) {
            predicateList.add(builder.equal(builder.lower(destinationItem.get("name")), destination.toLowerCase().trim()));
        }

        Predicate[] predicates = predicateList.toArray(new Predicate[0]);

        return em.createQuery(query.select(root).where(predicates)).getResultList();
    }
}
