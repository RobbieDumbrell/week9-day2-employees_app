package controllers;

import db.DBHelper;
import models.Department;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

//        INDEX
        get("/departments", (req, res) -> {

            List<Department> departments = DBHelper.getAll(Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        NEW
        get("/departments/new", (req, res) -> {

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/create.vtl");

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        CREATE
        post("/departments", (req, res) -> {

            String title = req.queryParams("title");

            Department addingDepartment = new Department(title);
            DBHelper.save(addingDepartment);

            res.redirect("/departments");
            return null;
        }, velocityTemplateEngine);


//        SHOW
        get("/departments/:id", (req, res) -> {

            int departmentId = Integer.parseInt(req.params(":id"));

            Department foundDepartment = DBHelper.find(departmentId, Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/show.vtl");
            model.put("foundDepartment", foundDepartment);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        EDIT
        get("/departments/:id/edit", (req, res) -> {

            int departmentId = Integer.parseInt(req.params(":id"));
            Department editingDepartment = DBHelper.find(departmentId, Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/edit.vtl");
            model.put("editingDepartment", editingDepartment);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);

//        UPDATE
        post("/departments/:id", (req, res) -> {

            String title = req.queryParams("title");

            int departmentId = Integer.parseInt(req.params(":id"));

            Department updatingDepartment = DBHelper.find(departmentId, Department.class);
            updatingDepartment.setTitle(title);
            DBHelper.save(updatingDepartment);

            res.redirect("/departments");
            return null;

        }, velocityTemplateEngine);

//        DESTROY
        post("/departments/:id/delete", (req, res) -> {

            int departmentId = Integer.parseInt(req.params(":id"));

            DBHelper.deleteById(departmentId, Department.class);

            res.redirect("/departments");
            return null;

        });

    }


}
