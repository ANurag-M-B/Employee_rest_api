package com.example.employee.dao;

import java.util.List;

import com.example.employee.entity.Employee;

public interface EmployeeDAO {
	
	List<Employee> findAll();

}
