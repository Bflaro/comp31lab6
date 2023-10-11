package comp31.formdemo.services;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comp31.formdemo.model.Employee;
import comp31.formdemo.repositories.Accounts;

@Service
public class LoginService {

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    Accounts accounts;

    public LoginService() {
        this.accounts = new Accounts();

    }

    public void addEmployee(String userId, String department, String password) {
        logger.info("Adding user: " + userId);
        Employee employee = new Employee(userId, department, password);
        accounts.add(employee);
    } 

    public void addEmployee(Employee employee) {
        accounts.add(employee);
    }

    public Employee findByUserId(String userId) {
        return accounts.findByUserId(userId);
    }

        public ArrayList<Employee> findAllEmployees() {
        return accounts.findAllEmployees();
    }

}
