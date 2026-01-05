package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event.ID;

import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   List<Employee> findByStatus(EmployeeStatus status);

	Optional<Employee> findByEmail(String email);
	List<Employee> findByApprovedFalse();

}
