package com.org.vaild.controller;

import com.org.vaild.customException.BusinessException;
import com.org.vaild.customException.ControllerException;
import com.org.vaild.entity.Employee;
import com.org.vaild.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee empsaved = employeeService.addEmployee(employee);
            return new ResponseEntity<>(empsaved, HttpStatus.CREATED);
        } catch (BusinessException e) {
            ControllerException ce=new ControllerException(e.getErrorCode(), e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            ControllerException ce=new ControllerException("611","something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> listOfAllemps=employeeService.getAllEmployees();
        return new ResponseEntity<>(listOfAllemps, HttpStatus.OK);
    }

    @GetMapping("/emp/{empid}")
    public ResponseEntity<?> getEmpById(@PathVariable("empid") int id) {
        try {
            Employee retrivedEmp = employeeService.getEmpById(id);
            return new ResponseEntity<>(retrivedEmp, HttpStatus.CREATED);
        } catch (BusinessException e) {
            ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ControllerException ce = new ControllerException("612", "something went wrong in controller");
            return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{empid}")
    public ResponseEntity<String> deleteById(@PathVariable("empid") int id){
        employeeService.deleteById(id);
        return new ResponseEntity<>("successfull deleted record= " + id, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee employeeSaved=employeeService.addEmployee(employee);
        return new ResponseEntity<Employee>(employeeSaved, HttpStatus.CREATED);
    }
}
