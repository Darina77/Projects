package com.daryna.Controller;

import com.daryna.Models.Data.Department;
import com.daryna.Models.Data.DepartmentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsController {

    private static String tableName = "Departments";
    private DbAccess access;

    public DepartmentsController(DbAccess access)
    {
        this.access = access;
    }

    public List<Department> getAllDepartments()
    {
        access.connectionDb();
        String sql = "select * from " + tableName;
        List<Department> departments = new ArrayList<>();
        ResultSet res = access.getSet(sql);
        try {
            if (res != null)
            {
                while (res.next()){
                    departments.add(new Department(res.getInt(1), res.getString(2), res.getString(3)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return departments;
    }

    public boolean addDepartmetn(Department newDep)
    {
        access.connectionDb();
        String sql = "insert into " + tableName + " (`nameDep`, `phoneDep`) values ('" + newDep.getName() + "', '" + newDep.getPhoneNumber()+"')";
        boolean res = access.add(sql);
        access.disConnect();
        return res;
    }

    public boolean removeDepartment(int depId)
    {
        access.connectionDb();
        String sql = "delete from " + tableName + " where ( `idDep` = '" + depId + "')";
        boolean res = access.update(sql);
        access.disConnect();
        return res;
    }

    public boolean updateDep(List<Department> all)
    {
        access.connectionDb();
        boolean res = true;
        for (Department dep: all)
        {
            String sql = "update " + tableName + " set `nameDep` = '" + dep.getName() + "', `phoneDep` = '"
                    + dep.getPhoneNumber() + "' where (`idDep` ='"+ dep.getId() + "')";
            res = access.update(sql);
            if(!res) break;
        }
        access.disConnect();
        return res;
    }

    public List<DepartmentInfo> getDepInfo(String depName, String date)
    {
        access.connectionDb();
        List<DepartmentInfo> departmentsInfos = new ArrayList<>();
        String sql = "select `idDep` from " + tableName + " where (`nameDep` = '" + depName + "')";
        ResultSet resDep = access.getSet(sql);
        try {
            if (resDep.next()) {
                int idDep = resDep.getInt(1);
                String sql2 = "select `namePos`, `salary`, `Positions`.`idEmp`, `surname` " +
                        "from (`Positions` INNER JOIN `Employees` ON `Positions`.`idEmp` = `Employees`.`idEmp`) " +
                        "where (`idDep` = '" + idDep + "' AND  `startDate` <= '" + date +"' AND (`endDate` IS NULL OR `endDate` >= '" + date + "'))";
                ResultSet res = access.getSet(sql2);
                try {
                    if (res != null) {
                        while (res.next()) {
                            departmentsInfos.add(new DepartmentInfo(res.getString(1), res.getDouble(2), res.getInt(3), res.getString(4)));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return departmentsInfos;
    }
}
