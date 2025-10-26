package net.cloudy.sytes.hello_liberty;

import org.springframework.data.jpa.repository.JpaRepository;

    interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
