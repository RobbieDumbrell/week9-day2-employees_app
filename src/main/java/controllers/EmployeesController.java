package controllers;

import db.DBHelper;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class EmployeesController {

    public EmployeesController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/employees", (req, res) -> {

            List<Employee> employees = DBHelper.getAll(Employee.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("employees", employees);
            model.put("template", "templates/employees/index.vtl");

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);
    }

}
