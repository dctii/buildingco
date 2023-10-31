package com.solvd.buildingco.stakeholders;

import com.solvd.buildingco.utilities.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Person implements PersonalName, PhoneNumbers, Addresses, Emails {
    private String[] nameParts;
    private String[] postNominals;
    // TODO: consider creating objects for building addresses
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

    @Override
    public String getForename() {
        return nameParts[0];
    }

    public void setForename(String forename) {
        nameParts[0] = forename;
    }

    @Override
    public String getMiddleName() {
        return nameParts[1];
    }

    public void setMiddleName(String middleName) {
        nameParts[1] = middleName;
    }

    @Override
    public String getSurname() {
        return nameParts[2];
    }

    public void setSurname(String surname) {
        nameParts[2] = surname;
    }

    @Override
    public String getSuffix() {
        return nameParts[3];
    }

    public void setSuffix(String suffix) {
        nameParts[3] = suffix;
    }

    @Override
    public String[] getPostNominals() {
        return postNominals;
    }

    public void setPostNominals(String[] postNominals) {
        this.postNominals = postNominals;
    }

    @Override
    public String getFullName() {
        return buildFullName(new String[]{});
    }

    public String buildFullName(String[] exclusions) {
        List<String> exclusionList = new ArrayList<>();

        if (exclusions != null) {
            exclusionList = Arrays.asList(exclusions);
        }

        StringBuilder fullName = new StringBuilder();

        String[] fieldNames = {"forename", "middleName", "surname", "suffix", "postNominals"};

        for (String fieldName : fieldNames) {
            if (!exclusionList.contains(fieldName)) {
                try {
                    Field field = this.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object value = field.get(this);
                    if (value != null) {
                        if (fullName.length() > 0) {
                            fullName.append(fieldName.equals("postNominals") ? ", " : " ");
                        }
                        if (fieldName.equals("postNominals")) {
                            fullName.append(String.join(", ", (String[]) value));
                        } else {
                            fullName.append(value.toString());
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
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
            builder.setLength(builder.length() - 2); // removes the last 2 chars
        }

        builder.append("}");
        return builder.toString();
    }
}
