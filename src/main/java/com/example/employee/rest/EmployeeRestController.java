package com.example.employee.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.dao.EmployeeDAO;
import com.example.employee.entity.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeDAO employeeDAO;
	
	// quick and dirty: inject employee dao (use constructor injection)
	public EmployeeRestController(EmployeeDAO theEmployeeDAO) {
		employeeDAO = theEmployeeDAO;
	}
	
	// expose "/employees" and return a list of employees
	@GetMapping("/employee/all")
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

}
