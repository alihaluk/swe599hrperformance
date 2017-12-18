package tr.edu.boun.hrperformance.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haluks on 16.12.2017.
 */

public class Employee
{
    public String uid;
    public String name;
    public String email;
    public String pass;
    public Map<String, Boolean> groups = new HashMap<>();

    public Employee()
    {

    }

    public Employee(String name, String email, String pass)
    {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }
}
