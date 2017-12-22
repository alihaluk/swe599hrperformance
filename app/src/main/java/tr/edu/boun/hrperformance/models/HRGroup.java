package tr.edu.boun.hrperformance.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haluks on 20.12.2017.
 */

public class HRGroup
{
    public String uid;
    public String name;
    public Map<String, Boolean> members = new HashMap<>();

    public HRGroup()
    {

    }

    public HRGroup(String uid, String name, Map<String, Boolean> members)
    {
        this.uid = uid;
        this.name = name;
        this.members = members;
    }
}
