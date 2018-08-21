package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

    public ManagersController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

//        INDEX
        get("/managers", (req, res) -> {

            List<Manager> managers = DBHelper.getAll(Manager.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        NEW
        get("/managers/new", (req, res) -> {

            List<Department> allDepartments = DBHelper.getAll(Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/create.vtl");
            model.put("allDepartments", allDepartments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        CREATE
        post("/managers", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            int salary = Integer.parseInt(req.queryParams("salary"));
            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager addingManager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(addingManager);

            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);


//        SHOW
        get("/managers/:id", (req, res) -> {

            int managerId = Integer.parseInt(req.params(":id"));

            Manager foundManager = DBHelper.find(managerId, Manager.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/show.vtl");
            model.put("foundManager", foundManager);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        EDIT
        get("/managers/:id/edit", (req, res) -> {

            int managerId = Integer.parseInt(req.params(":id"));
            Manager editingManager = DBHelper.find(managerId, Manager.class);

            List<Department> allDepartments = DBHelper.getAll(Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/edit.vtl");
            model.put("editingManager", editingManager);
            model.put("allDepartments", allDepartments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        UPDATE
        post("/managers/:id", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            int salary = Integer.parseInt(req.queryParams("salary"));
            double budget = Double.parseDouble(req.queryParams("budget"));

            int managerId = Integer.parseInt(req.params(":id"));

            Manager updatingManager = DBHelper.find(managerId, Manager.class);
            updatingManager.setFirstName(firstName);
            updatingManager.setLastName(lastName);
            updatingManager.setSalary(salary);
            updatingManager.setDepartment(department);
            updatingManager.setBudget(budget);
            DBHelper.save(updatingManager);

            res.redirect("/managers");
            return null;

        }, velocityTemplateEngine);

//        DESTROY
        post("/managers/:id/delete", (req, res) -> {

            int managerId = Integer.parseInt(req.params(":id"));

            DBHelper.deleteById(managerId, Manager.class);

            res.redirect("/managers");
            return null;

        });


    }

}
