package com.giczi.david.flight.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.giczi.david.flight.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String roleName);
	void deleteById(Long id);
	List<Role> findAll();
	
}

