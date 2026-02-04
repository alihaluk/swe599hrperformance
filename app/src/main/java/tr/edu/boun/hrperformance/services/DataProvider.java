package tr.edu.boun.hrperformance.services;

import java.util.List;

import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;

public interface DataProvider {
    interface DataCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    class LoginResult {
        public String userType;
        public String userId;

        public LoginResult(String userType, String userId) {
            this.userType = userType;
            this.userId = userId;
        }
    }

    void login(String email, String password, DataCallback<LoginResult> callback);
    void getEmployeeTasks(String employeeId, DataCallback<List<EmployeeTask>> callback);
    void updateTask(EmployeeTask task, DataCallback<Void> callback);
    void getEmployee(String employeeId, DataCallback<Employee> callback);
    void updateEmployee(Employee employee, DataCallback<Void> callback);
    void getAllEmployees(DataCallback<List<Employee>> callback);
}
