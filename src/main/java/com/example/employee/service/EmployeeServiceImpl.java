package com.example.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.dao.EmployeeDAO;
import com.example.employee.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	// constructor DAO injection
	private EmployeeDAO employeeDAO;
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO theEmployeeDAO) {
		employeeDAO = theEmployeeDAO;
	}
	@Override
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

}
