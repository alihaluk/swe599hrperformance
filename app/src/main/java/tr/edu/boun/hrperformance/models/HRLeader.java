package tr.edu.boun.hrperformance.models;

/**
 * Created by haluks on 17.12.2017.
 */

public class HRLeader
{
    public String uid;
    public String name;
    public String email;
    public String pass;

    public HRLeader()
    {

    }

    public HRLeader(String name, String email, String pass)
    {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }
}
