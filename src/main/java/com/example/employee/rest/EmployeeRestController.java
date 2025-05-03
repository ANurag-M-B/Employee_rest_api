package com.example.employee.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	private ObjectMapper objectMapper;
	
	// quick and dirty: inject employee dao (use constructor injection)
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService, ObjectMapper theObjectMapper) {
		employeeService = theEmployeeService;
		objectMapper = theObjectMapper;
	}
	
	// expose "/employees" and return a list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}
	
	// add mapping for get employee by id
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable("employeeId") int employeeId) {
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (null == theEmployee) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// pass id in JSON set id to 0 
		// this is to force a save of new item instead of update
		theEmployee.setId(0);
		Employee dbEmployee = employeeService.save(theEmployee);
		return dbEmployee;
	}
	
	// add PUT mapping to update existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		Employee dbEmployee = employeeService.save(theEmployee);
		
		return dbEmployee;
	}
	
	// add mapping for PATCH to partially update an employee
	
	@PatchMapping("/employees/{employeeId}")
	public Employee patchEmployee(@PathVariable("employeeId") int employeeId, @RequestBody Map<String, Object> patchPayLoad) {
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if (null == tempEmployee) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		if (patchPayLoad.containsKey("id")) {
			throw new RuntimeException("Employee id not allowed in request body - " + employeeId);
		}
		
		Employee patchedEmployee = apply(patchPayLoad, tempEmployee);
		
		Employee dbEmployee = employeeService.save(patchedEmployee);
		
		return dbEmployee;
		
	}
	/**
	 * merge partial data from patch request and data available in database
	 * */
	private Employee apply(Map<String, Object> patchPayLoad, Employee tempEmployee) {
		// convert employee object to a json object node
		ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);
		
		// convert patchPayLoad to a json object node
		ObjectNode patchNode = objectMapper.convertValue(patchPayLoad, ObjectNode.class);
		
		// Merge the patch update into the employee node
		employeeNode.setAll(patchNode);
		
		// return converted node
		
		return objectMapper.convertValue(employeeNode, Employee.class);
	}
	
	/**
	 * delete an employee
	 * */
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") int employeeId) {
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if (null == tempEmployee) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
				
		employeeService.deleteById(employeeId);
		
		return "Deleted employee with id - " + employeeId;
		
	}

}
