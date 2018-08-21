package controllers;

import db.Seeds;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;

import java.util.HashMap;

public class MainController {

    public static void main(String[] args) {

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
//        Seeds.seedData();

        EmployeesController employeesController = new EmployeesController();
        ManagersController managersController = new ManagersController();
        EngineersController engineersController = new EngineersController();
        DepartmentsController departmentsController = new DepartmentsController();

        get("/", (req, res) -> {

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.vtl" );

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

    }

}
