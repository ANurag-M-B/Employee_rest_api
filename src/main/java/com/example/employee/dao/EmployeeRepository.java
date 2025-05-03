package com.example.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.entity.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
