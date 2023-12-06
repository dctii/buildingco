package com.solvd.buildingco.multithreading;

import com.solvd.buildingco.utilities.StringFormatters;

public class Connection {
    private String name;

    public Connection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        Class<?> currClass = Connection.class;
        String[] fieldNames = {
                "name"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
