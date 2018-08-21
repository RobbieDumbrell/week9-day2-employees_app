package controllers;

import db.DBHelper;
import models.Manager;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

public class ManagersController {

    public ManagersController() {
        setUpEndPoints();
    }

    private void setUpEndPoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/managers", (req, res) -> {

            List<Manager> managers = DBHelper.getAll(Manager.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);

            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);
    }

}
