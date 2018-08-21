package controllers;

import db.Seeds;
import spark.Spark;

public class MainController {

    public static void main(String[] args) {

//        Seeds.seedData();

        //        allows / on the end of path, taken from Stack Overflow: https://stackoverflow.com/questions/34753001/spark-framework-match-with-or-without-trailing-slash

//        Spark.before((req, res) -> {
//            String path = req.pathInfo();
//            if (path.endsWith("/"))
//                res.redirect(path.substring(0, path.length() - 1));
//        });

        EmployeesController employeesController = new EmployeesController();
        ManagersController managersController = new ManagersController();

    }

}
