package com.org.vaild.service;

import com.org.vaild.customException.BusinessException;
import com.org.vaild.entity.Employee;
import com.org.vaild.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee addEmployee(Employee employee) {

           if (employee.getName().isEmpty() || employee.getName().length()==0 ||employee.getName().isBlank()) {
               throw new BusinessException("601", "please send proper name,its blank");
           }
           try {
           Employee savedEmp=employeeRepo.save(employee);
           return savedEmp;
       } catch (IllegalArgumentException e) {
           throw new BusinessException("602","given employee is null" + e.getMessage());
       }catch (Exception e) {
           throw new BusinessException("603","something went wrong with service layer while saving the data" + e.getMessage());
       }
    }

    public List<Employee> getAllEmployees() {
        try {
            List<Employee> empList = employeeRepo.findAll();
            if (empList.isEmpty())
                throw new BusinessException("604", "list is completely empty,we have nothing to return");
            return empList;
        } catch (Exception e) {
            throw new BusinessException("605","something went wrong with service layer while fetching all employees" + e.getMessage());
        }
    }

    public Employee getEmpById(int id) {
        try {
            return employeeRepo.findById(id).get();
        }catch (IllegalArgumentException e){
            throw new BusinessException("606","given employee id is null " + e.getMessage());
        }catch (NoSuchElementException e){
            throw new BusinessException("607","give emp id is doesnt exist in db" + e.getMessage());
        }
    }

    public void deleteById(int id) {
        try {
             employeeRepo.deleteById(id);
        }catch (IllegalArgumentException e){
            throw new BusinessException("608","given employee id is null ,please send some id to be searched" + e.getMessage());
        }
    }
}
