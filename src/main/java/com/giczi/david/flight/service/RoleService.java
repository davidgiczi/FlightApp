package com.giczi.david.flight.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.giczi.david.flight.domain.Role;
import com.giczi.david.flight.repository.RoleRepository;

@Service
public class RoleService {
	
	
	private RoleRepository roleRepo;
	
	
	@Autowired
	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}


	public List<String> getRoleStringStore(){
			
	List<Role> roles = roleRepo.findAll();
	
	List<String> roleStringStore = roles.stream().map(r -> r.getRole()).collect(Collectors.toList());
		
	return roleStringStore;
	}
	
	
	public String getPassengerRoleAsString(Set<Role> passengerRoles) {
		
		List<Role> roles = roleRepo.findAll();
		String roleString = "ROLE_USER";
		
		for (Role role : roles) {
			if(passengerRoles.contains(role)) {
				roleString = role.getRole();
			}
		}
		
		return roleString;
		
	}
	
	public Role findByRole(String roleName) {
		return roleRepo.findByRole(roleName);
	}
	
}
