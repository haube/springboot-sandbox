package net.cloudy.sytes.hello_liberty.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cloudy.sytes.hello_liberty.jpa.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
