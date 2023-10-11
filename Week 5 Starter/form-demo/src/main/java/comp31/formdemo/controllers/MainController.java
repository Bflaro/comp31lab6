package comp31.formdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import comp31.formdemo.model.Employee;
import comp31.formdemo.services.LoginService;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    LoginService loginService;

    public MainController(LoginService loginService) {
        this.loginService = loginService;

        loginService.addEmployee("Joe","orders","Joe");
        loginService.addEmployee("Moe","orders","Moe");
        loginService.addEmployee("Mary","sales","Mary");
        loginService.addEmployee("Jane","sales","Jane");
        loginService.addEmployee("Josh","admin","Josh");
        loginService.addEmployee("Bill","admin","Bill");

    }

    @GetMapping("/")
    String getRoot(Model model) {
        logger.info("---- At root.");
        Employee employee = new Employee(); // Create backing object and
        model.addAttribute("employee", employee); // send it to login form
        return "login-form";
    }

    @PostMapping("/login")
    public String getForm(Employee employee, Model model) {
        logger.info("---- At /login.");
        logger.info("---- " + employee.toString());
        Employee currentUser = loginService.findByUserId(employee.getUserId());
        String returnPage;
        if (currentUser == null) {
            model.addAttribute("employee", employee);
            returnPage = "login-form";
        } 
        else {
            logger.info("---- current userId " + currentUser.getUserId());
            logger.info("---- current department " + currentUser.getDepartment());

            model.addAttribute("employee", currentUser);

            if(currentUser.getDepartment() == null){  returnPage = "login-form";  }
            else{
                if(employee.getPassword().equals(currentUser.getPassword()))
                    {  returnPage = "departments/" + currentUser.getDepartment();  }

                else{  returnPage = "login-form";  }
            }    
        }
        return returnPage;
    }

@GetMapping("/admin")
    public String getRegister(Model model)
    {   
        model.addAttribute("employee", new Employee());
        return "departments/admin";
    }

    @PostMapping("/admin")
    public String postRegister(Employee employee) {

     loginService.addEmployee(employee);
       return "redirect:/admin";
    }
}
