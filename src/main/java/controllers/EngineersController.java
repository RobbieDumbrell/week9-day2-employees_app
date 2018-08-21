package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {

    public EngineersController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

//        INDEX
        get("/engineers", (req, res) -> {

            List<Engineer> engineers = DBHelper.getAll(Engineer.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");
            model.put("engineers", engineers);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        NEW
        get("/engineers/new", (req, res) -> {

            List<Department> allDepartments = DBHelper.getAll(Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/create.vtl");
            model.put("allDepartments", allDepartments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        CREATE
        post("/engineers", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            int salary = Integer.parseInt(req.queryParams("salary"));

            Engineer addingEngineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(addingEngineer);

            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);


//        SHOW
        get("/engineers/:id", (req, res) -> {

            int engineerId = Integer.parseInt(req.params(":id"));

            Engineer foundEngineer = DBHelper.find(engineerId, Engineer.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/show.vtl");
            model.put("foundEngineer", foundEngineer);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        EDIT
        get("/engineers/:id/edit", (req, res) -> {

            int engineerId = Integer.parseInt(req.params(":id"));
            Engineer editingEngineer = DBHelper.find(engineerId, Engineer.class);

            List<Department> allDepartments = DBHelper.getAll(Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/edit.vtl");
            model.put("editingEngineer", editingEngineer);
            model.put("allDepartments", allDepartments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        UPDATE
        post("/engineers/:id", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            int salary = Integer.parseInt(req.queryParams("salary"));

            int engineerId = Integer.parseInt(req.params(":id"));

            Engineer updatingEngineer = DBHelper.find(engineerId, Engineer.class);
            updatingEngineer.setFirstName(firstName);
            updatingEngineer.setLastName(lastName);
            updatingEngineer.setSalary(salary);
            updatingEngineer.setDepartment(department);
            DBHelper.save(updatingEngineer);

            res.redirect("/engineers");
            return null;

        }, velocityTemplateEngine);

//        DESTROY
        post("/engineers/:id/delete", (req, res) -> {

            int engineerId = Integer.parseInt(req.params(":id"));

            DBHelper.deleteById(engineerId, Engineer.class);

            res.redirect("/engineers");
            return null;

        });

    }


}
