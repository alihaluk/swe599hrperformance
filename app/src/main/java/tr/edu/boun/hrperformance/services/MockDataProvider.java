package tr.edu.boun.hrperformance.services;

import java.util.ArrayList;
import java.util.List;

import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.models.HRLeader;

public class MockDataProvider implements DataProvider {

    private static MockDataProvider instance;
    private List<Employee> employees;
    private List<HRLeader> hrLeaders;
    private List<EmployeeTask> employeeTasks;

    private MockDataProvider() {
        employees = new ArrayList<>();
        hrLeaders = new ArrayList<>();
        employeeTasks = new ArrayList<>();

        // Seed Data
        Employee emp1 = new Employee("Haluk Seven", "haluk.seven@boun.edu.tr", "haluks");
        emp1.uid = "emp1";
        employees.add(emp1);

        HRLeader hr1 = new HRLeader("Sertac", "sertac@boun.edu.tr", "sertac");
        hr1.uid = "hr1";
        hrLeaders.add(hr1);

        EmployeeTask task1 = new EmployeeTask("task1", "Blockchain Research", "hr1", "emp1", "Research blockchain", "2017-12-20");
        employeeTasks.add(task1);

        EmployeeTask task2 = new EmployeeTask("task2", "Firebase Video", "hr1", "emp1", "Watch video", "2017-12-19");
        employeeTasks.add(task2);

        EmployeeTask task3 = new EmployeeTask("task3", "Fill review form", "hr1", "emp1", "Fill the annual review form", "2017-12-27");
        employeeTasks.add(task3);
    }

    public static MockDataProvider getInstance() {
        if (instance == null) {
            instance = new MockDataProvider();
        }
        return instance;
    }

    @Override
    public void login(String email, String password, final DataCallback<LoginResult> callback) {
        for (Employee e : employees) {
            if (e.email.equals(email) && e.pass.equals(password)) {
                callback.onSuccess(new LoginResult("employee", e.uid));
                return;
            }
        }
        for (HRLeader hr : hrLeaders) {
            if (hr.email.equals(email) && hr.pass.equals(password)) {
                callback.onSuccess(new LoginResult("hrleader", hr.uid));
                return;
            }
        }
        callback.onError(new Exception("Invalid credentials"));
    }

    @Override
    public void getEmployeeTasks(String employeeId, DataCallback<List<EmployeeTask>> callback) {
        List<EmployeeTask> result = new ArrayList<>();
        for (EmployeeTask task : employeeTasks) {
            if (task.employee.equals(employeeId)) {
                result.add(task);
            }
        }
        callback.onSuccess(result);
    }

    @Override
    public void updateTask(EmployeeTask task, DataCallback<Void> callback) {
        for (int i = 0; i < employeeTasks.size(); i++) {
            if (employeeTasks.get(i).uid.equals(task.uid)) {
                employeeTasks.set(i, task);
                callback.onSuccess(null);
                return;
            }
        }
        callback.onError(new Exception("Task not found"));
    }

    @Override
    public void getEmployee(String employeeId, DataCallback<Employee> callback) {
         for (Employee e : employees) {
            if (e.uid.equals(employeeId)) {
                callback.onSuccess(e);
                return;
            }
        }
        callback.onError(new Exception("Employee not found"));
    }

    @Override
    public void updateEmployee(Employee employee, DataCallback<Void> callback) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).uid.equals(employee.uid)) {
                employees.set(i, employee);
                callback.onSuccess(null);
                return;
            }
        }
        callback.onError(new Exception("Employee not found"));
    }

    @Override
    public void getAllEmployees(DataCallback<List<Employee>> callback) {
        callback.onSuccess(new ArrayList<>(employees));
    }
}
