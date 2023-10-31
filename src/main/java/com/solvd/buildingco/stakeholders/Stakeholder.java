package com.solvd.buildingco.stakeholders;

/*TODO: consider creating a PaymentInfo class that can be implemented into Stakeholder
 * fields: billingAddress, bankAccNum, bankRoutingNum, etc.
 * */

import com.solvd.buildingco.utilities.FieldUtils;

import java.util.Arrays;

public class Stakeholder extends Person {
    private String[] organizationNames;
    private String[] roles;

    public Stakeholder(String[] nameParts, String[] postNominals, String[] organizationNames,
                       String[] roles, String[] addresses, String[] phoneNumbers, String[] emails) {
        super(nameParts, postNominals, addresses, emails, phoneNumbers);
        this.organizationNames = organizationNames;
        this.roles = roles;
    }

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
        String className = "Stakeholder";
        String personStr = super.toString();
        String[] fieldNames = {"organizationNames", "roles"};

        StringBuilder builder = new StringBuilder(className + "{");
        builder.append(personStr);

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);
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
