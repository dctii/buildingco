package com.solvd.buildingco.stakeholders;

import com.solvd.buildingco.utilities.FieldUtils;

import java.util.Arrays;

public abstract class Person {
    private String[] nameParts;
    private String[] postNominals;
    private String[] addresses;
    private String[] emails;
    private String[] phoneNumbers;

    public Person(String[] nameParts, String[] postNominals, String[] addresses,
                  String[] emails, String[] phoneNumbers) {
        this.nameParts = nameParts;
        this.postNominals = postNominals;
        this.addresses = addresses;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getNameParts() {
        return nameParts;
    }

    public String getForename() {
        return nameParts[0];
    }

    public void setForename(String forename) {
        nameParts[0] = forename;
    }

    public String getMiddleName() {
        return nameParts[1];
    }

    public void setMiddleName(String middleName) {
        nameParts[1] = middleName;
    }

    public String getSurname() {
        return nameParts[2];
    }

    public void setSurname(String surname) {
        nameParts[2] = surname;
    }

    public String getSuffix() {
        return nameParts[3];
    }

    public void setSuffix(String suffix) {
        nameParts[3] = suffix;
    }

    public String[] getPostNominals() {
        return postNominals;
    }

    public void setPostNominals(String[] postNominals) {
        this.postNominals = postNominals;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();

        // Directly use the nameParts array
        if (nameParts != null) {
            for (String namePart : nameParts) {
                if (namePart != null) {
                    if (fullName.length() > 0) {
                        fullName.append(" ");
                    }
                    fullName.append(namePart);
                }
            }
        }

        if (getPostNominals() != null && getPostNominals().length > 0) {
            fullName.append(", ").append(String.join(", ", getPostNominals()));
        }

        return fullName.toString();
    }


    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails() {
        this.emails = emails;
    }

    @Override
    public String toString() {
        String className = "Person";
        StringBuilder builder = new StringBuilder(className + "{");
        String[] fieldNames = {"nameParts", "postNominals", "addresses", "emails", "phoneNumbers"};

        for (String fieldName : fieldNames) {
            Object fieldValue = FieldUtils.getField(this, fieldName);

            if (fieldValue != null) {
                builder
                        .append(fieldName)
                        .append("=")
                        .append(Arrays.toString(
                                (Object[]) fieldValue
                        ))
                        .append(", ");
            }
        }

        if (builder.length() > (className + "{").length()) {
            builder.setLength(builder.length() - 2);
        }

        builder.append("}");
        return builder.toString();
    }
}
