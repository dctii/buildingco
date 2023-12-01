package com.solvd.buildingco.stakeholders;

import com.solvd.buildingco.utilities.BooleanUtils;
import com.solvd.buildingco.utilities.ReflectionUtils;

import java.util.Arrays;



// Person but adds organizationNames and roles
public abstract class Stakeholder extends Person {

    private String[] organizationNames;
    private String[] roles;

    public Stakeholder() {
        super();
    }
    public Stakeholder(String[] nameParts) {
        super(nameParts);
    }

    public Stakeholder(String[] nameParts, String[] postNominals, String[] organizationNames,
                       String[] roles, String[] addresses, String[] phoneNumbers, String[] emails) {
        super(nameParts, postNominals, addresses, emails, phoneNumbers);
        this.organizationNames = organizationNames;
        this.roles = roles;
    }

    // getters and setters
    public String[] getOrganizationNames() {
        return BooleanUtils.isEmptyOrNullArray(organizationNames)
                ? null
                : organizationNames;
    }

    public void setOrganizationNames(String[] organizationNames) {
        this.organizationNames = organizationNames;
    }

    public String[] getRoles() {
        return BooleanUtils.isEmptyOrNullArray(roles)
                ? null
                : roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        String className = "Stakeholder";
        String personStr = super.toString();
        String[] fieldNames = {"organizationNames", "roles"};

        StringBuilder builder = new StringBuilder(className + "{");
        builder.append(personStr);

        for (String fieldName : fieldNames) {
            Object fieldValue = ReflectionUtils.getField(this, fieldName);
            if (fieldValue != null) {
                builder
                        .append(", ")
                        .append(fieldName)
                        .append("=")
                        .append(Arrays.toString(
                                (Object[]) fieldValue
                        ));

            }
        }

        builder.append("}");

        return builder.toString();

    }

}
