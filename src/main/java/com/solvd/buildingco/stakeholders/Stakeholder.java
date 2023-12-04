package com.solvd.buildingco.stakeholders;

import com.solvd.buildingco.utilities.StringFormatters;

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
        return organizationNames;
    }

    public void setOrganizationNames(String[] organizationNames) {
        this.organizationNames = organizationNames;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        Class<?> currClass = Stakeholder.class;
        String[] fieldNames = {
                "organizationNames",
                "roles"
        };

        String parentToString = super.toString();
        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(
                currClass,
                fieldNames,
                parentToString,
                fieldsString
        );
    }


}
