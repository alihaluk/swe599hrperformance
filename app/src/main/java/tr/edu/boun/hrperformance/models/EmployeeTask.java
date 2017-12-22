package tr.edu.boun.hrperformance.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haluks on 17.12.2017.
 */

@IgnoreExtraProperties
public class EmployeeTask
{
    public String uid;
    public String title;
    public String assigner;
    public String employee;
    public String body;
    public String dueTime;
    public String startTime;
    public String finishTime;

    public EmployeeTask()
    {

    }

    public EmployeeTask(String uid, String title, String assigner, String employee, String body, String duetime)
    {
        this.uid = uid;
        this.title = title;
        this.assigner = assigner;
        this.employee = employee;
        this.body = body;
        this.dueTime = duetime;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("assigner", assigner);
        result.put("employee", employee);
        result.put("body", body);
        result.put("dueTime", dueTime);

        return result;
    }
}
