package com.daryna.Controller;

import com.daryna.Models.Data.Employee;
import com.daryna.Models.Data.EmployeeInfoHead;
import com.daryna.Models.Data.EmployeeInfoPos;
import com.daryna.Models.Data.EmployeeInfoWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesController {
    private static String tableName = "Employees";

    private DbAccess access;

    public EmployeesController(DbAccess access) {
        this.access = access;
    }

    public List<Employee> getAllEmployees() {
        access.connectionDb();
        String sql = "select * from " + tableName;
        List<Employee> employees = new ArrayList<>();
        ResultSet res = access.getSet(sql);
        try {
            if (res != null) {
                while (res.next()) {
                    employees.add(new Employee(res.getInt(1), res.getString(2), res.getString(3).charAt(0), res.getString(4)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return employees;
    }

    public boolean addEmployee(Employee newEmp) {
        access.connectionDb();
        String sql = "insert into " + tableName
                + " (`surname`, `sex`, `birthday`) values ('" + newEmp.getSurname()
                + "', '" + newEmp.getSex() + "', '" + newEmp.getBirthday() + "')";
        boolean res = access.add(sql);
        access.disConnect();
        return res;
    }

    public boolean removeEmployee(int idEmp) {
        access.connectionDb();
        String sql = "delete from " + tableName + " where ( `idEmp` = '" + idEmp + "')";
        boolean res = access.update(sql);
        access.disConnect();
        return res;
    }

    public boolean updateEmployee(List<Employee> all) {
        access.connectionDb();
        boolean res = true;
        for (Employee emp : all) {
            String sql = "update " + tableName + " set `surname` = '" + emp.getSurname() + "', `sex` = '"
                    + emp.getSex() + "', `birthday` = '" + emp.getBirthday()
                    + "' where (`idEmp` = '" + emp.getId() + "')";
            res = access.update(sql);
            if (!res) break;
        }
        access.disConnect();
        return res;
    }

    public List<EmployeeInfoPos> getEmployeePos(int emplId, String startDate, String endDate) {
        access.connectionDb();
        List<EmployeeInfoPos> emplInfo = new ArrayList<>();

        String sql2 = "select `namePos`, `salary`, `startDate`, `endDate`, `nameDep`" +
                "from (`Positions` INNER JOIN `Departments` ON `Positions`.`idDep` = `Departments`.`idDep`)" +
                "where (`idEmp` = '" + emplId + "' AND  `startDate` >= '" + startDate + "' AND (`endDate` IS NULL OR `endDate` <= '" + endDate + "'))";
        ResultSet res = access.getSet(sql2);
        try {
            if (res != null) {
                while (res.next()) {
                    emplInfo.add(new EmployeeInfoPos(res.getString(1), res.getString(5), res.getString(3), res.getString(4), res.getDouble(2)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return emplInfo;
    }

    public List<EmployeeInfoHead> getEmployeeHead(int emplId, String startDate, String endDate)
    {
        access.connectionDb();
        List<EmployeeInfoHead> headInfo = new ArrayList<>();

        String sql2 = "select `Heads`.`startDate`, `Heads`.`endDate`, `salary`, `nameProject`" +
                "from (`Heads` INNER JOIN `Projects` ON `Heads`.`idProject` = `Projects`.`id`)" +
                "where (`idEmp` = '" + emplId + "' AND  `Heads`.`startDate` >= '" + startDate + "' AND (`Heads`.`endDate` IS NULL OR `Heads`.`endDate` <= '" + endDate + "'))";
        ResultSet res = access.getSet(sql2);
        try {
            if (res != null) {
                while (res.next()) {
                    headInfo.add(new EmployeeInfoHead(res.getString(4), res.getString(1), res.getString(2), res.getDouble(3)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return headInfo;
    }


    public List<EmployeeInfoWork> getEmployeeWorks(int emplId, String startDate, String endDate) {
        access.connectionDb();
        List<EmployeeInfoWork> workInfo = new ArrayList<>();

        String sql2 = "select `startDate`, `endDate`, `descriptionWork`, `numStage`, `nameProject` " +
                "from ((select `idStage`, `numStage`, `nameProject` " +
                "from `Stages` " +
                "inner join `Projects` on `Stages`.`idProject` = `Projects`.`id`) as t " +
                "inner join `Works` on `t`.`idStage` = `Works`.`idStage`) " +
                "where (`idEmp` = '" + emplId + "' AND  `startDate` >= '" + startDate + "' AND (`endDate` IS NULL OR `endDate` <= '" + endDate + "'))";
        ResultSet res = access.getSet(sql2);
        try {
            if (res != null) {
                while (res.next()) {
                    workInfo.add(new EmployeeInfoWork(res.getString(3), res.getString(5), res.getInt(4), res.getString(1), res.getString(2)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return workInfo;
    }
}
