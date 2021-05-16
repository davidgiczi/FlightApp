package com.giczi.david.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.giczi.david.flight.domain.FlightTicket;

@Repository
public interface FlightTicketRepository extends CrudRepository<FlightTicket, Long> {
	
	List<FlightTicket> findAll();
	List<FlightTicket> findByPassengerId(Long id);
	@Query(value = "select * from tickets where passenger_id = :id and deleted = false", nativeQuery = true)
	List<FlightTicket> findNotDeletedTicketsByPassengerId(@Param("id") Long id);
	@Query(value = "select * from tickets"
			+ " where "
			+ "(passenger_id = :id and deleted = false and departure_place like %:text%)"
			+ " or "
			+ "(passenger_id = :id and deleted = false and CAST(departure_date AS TEXT) like %:text%)" 
			+ " or "
			+ "(passenger_id = :id and deleted = false and arrival_place like %:text%)"
			+ " or "
			+ "(passenger_id = :id and deleted = false and CAST(arrival_date AS TEXT) like %:text%)"
			+ " or "
			+ "(passenger_id = :id and deleted = false and flight_number like %:text%)"
			+ " or "
			+ "(passenger_id = :id and deleted = false and CAST(price AS TEXT) like %:text%)", nativeQuery = true)
	List<FlightTicket> findNotCancelledTicketsByTextAndPassengerId(@Param("text") String text, Long id);
	@Query(value = "select * from tickets"
			+ " where "
			+ "(passenger_id = :id and departure_place like %:text%)"
			+ " or "
			+ "(passenger_id = :id and CAST(departure_date AS TEXT) like %:text%)" 
			+ " or "
			+ "(passenger_id = :id and arrival_place like %:text%)"
			+ " or "
			+ "(passenger_id = :id and CAST(arrival_date AS TEXT) like %:text%)"
			+ " or "
			+ "(passenger_id = :id and flight_number like %:text%)"
			+ " or "
			+ "(passenger_id = :id and CAST(price AS TEXT) like %:text%)", nativeQuery = true)
	List<FlightTicket> findAllTicketsByTextAndPassengerId(@Param("text") String text, Long id);
	@Query(value = "select * from tickets where passenger_id = :id", nativeQuery = true)
	List<FlightTicket> findTicketsByPassengerId(@Param("id") Long id);
}
