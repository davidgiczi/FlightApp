package com.giczi.david.flight.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.giczi.david.flight.domain.Passenger;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
	
	List<Passenger> findAll();
	@Query(value = "select * from passengers where user_name = :user and password = :pass", nativeQuery = true)
	Passenger findPassengerByUsernameAndPassword(@Param("user") String user, @Param("pass") String pass);
	Passenger findByUserName(String username);
	Passenger findByActivation(String code);
	Optional<Passenger> findById(Long id);
	@Query(value = "select * from passengers where"
			+ " first_name like %:text%"
			+ " or "
			+ "last_name like %:text%" 
			+ " or "
			+ "CAST(date_of_birth AS TEXT) like %:text%"
			+ " or "
			+ "user_name like %:text%"
			+ " or "
			+ "activation like %:text%", nativeQuery = true)
	List<Passenger> findByText(@Param("text") String text);
	@Query(value = "select * from passengers where password like %:text%", nativeQuery = true)
	List<Passenger> findByPassword(@Param("text") String text);
	
}
