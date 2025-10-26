package net.cloudy.sytes.hello_liberty;
class EmployeeNotFoundException extends RuntimeException {

  EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
