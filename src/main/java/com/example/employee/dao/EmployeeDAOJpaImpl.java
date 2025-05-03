package com.example.employee.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.employee.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{
	
	// Define field for entity manager 
	private EntityManager entityManager;
	
	// set up constructor injection
	public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Employee> findAll() {
		// create a query
		TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee", Employee.class);
		
		// Execute query and get result
		List<Employee> employees = theQuery.getResultList();
		
		// return result
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		
		Employee theEmployee = entityManager.find(Employee.class, theId);
		return theEmployee;
	}

	@Override
	public Employee save(Employee theEmployee) {
		Employee dbEmployee = entityManager.merge(theEmployee);
		
		return dbEmployee;
	}

	@Override
	public void deleteById(int theId) {
		Employee theEmployee = entityManager.find(Employee.class, theId);
		
		entityManager.remove(theEmployee);
		
	}

}
