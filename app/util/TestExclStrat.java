package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by alexcosta on 4/13/16.
 */
public class TestExclStrat implements ExclusionStrategy {

    private Class<?> c;
    private String fieldName;
    public TestExclStrat(String fqfn) throws SecurityException, NoSuchFieldException, ClassNotFoundException
    {
        this.c = Class.forName(fqfn.substring(0, fqfn.lastIndexOf(".")));
        this.fieldName = fqfn.substring(fqfn.lastIndexOf(".")+1);
    }
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getDeclaringClass() == c && f.getName().equals(fieldName));
    }

}