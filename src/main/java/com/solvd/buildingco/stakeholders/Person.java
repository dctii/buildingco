package com.solvd.buildingco.stakeholders;

import com.solvd.buildingco.exception.InvalidValueException;
import com.solvd.buildingco.utilities.BooleanUtils;
import com.solvd.buildingco.utilities.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

// general Person class with usual title and contact information
public abstract class Person {
    private static final Logger LOGGER = LogManager.getLogger(Person.class);
    private String[] nameParts;
    private String[] postNominals;
    private String[] addresses;
    private String[] emails;
    private String[] phoneNumbers;

    final private String BLANK_FORENAME_MESSAGE = "The 'forename' cannot be blank or empty.";
    final private String BLANK_SURNAME_MESSAGE = "The 'surname' cannot be blank or empty.";

    public Person() {
    }

    public Person(String[] nameParts) {
        this.nameParts = nameParts;
    }

    public Person(String[] nameParts, String[] postNominals, String[] addresses, String[] emails, String[] phoneNumbers) {
        this.nameParts = nameParts;
        this.postNominals = postNominals;
        this.addresses = addresses;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }

    // getters and setters

    public String[] getNameParts() {
        return BooleanUtils.isEmptyOrNullArray(nameParts) ? null : nameParts;
    }

    public void setNameParts(String[] nameParts) {
        if (!BooleanUtils.isBlankOrEmptyString(nameParts[0])) {
            LOGGER.warn(BLANK_FORENAME_MESSAGE);
            throw new InvalidValueException(BLANK_FORENAME_MESSAGE);
        }

        if (!BooleanUtils.isBlankOrEmptyString(nameParts[2])) {
            LOGGER.warn(BLANK_SURNAME_MESSAGE);
            throw new InvalidValueException(BLANK_SURNAME_MESSAGE);
        }

        this.nameParts = nameParts;
    }

    public String getForename() {
        return BooleanUtils.isEmptyOrNullArray(nameParts) ? null : nameParts[0];
    }

    public void setForename(String forename) {
        if (BooleanUtils.isBlankOrEmptyString(forename)) {
            LOGGER.warn(BLANK_FORENAME_MESSAGE);
            throw new InvalidValueException(BLANK_FORENAME_MESSAGE);
        }
        nameParts[0] = forename;
    }

    public String getMiddleName() {
        return BooleanUtils.isEmptyOrNullArray(nameParts)
                ? null
                : nameParts[1];
    }

    public void setMiddleName(String middleName) {
        nameParts[1] = middleName;
    }

    public String getSurname() {
        return BooleanUtils.isEmptyOrNullArray(nameParts) ? null : nameParts[2];
    }

    public void setSurname(String surname) {
        if (BooleanUtils.isBlankOrEmptyString(surname)) {
            LOGGER.warn(BLANK_SURNAME_MESSAGE);
            throw new InvalidValueException(BLANK_SURNAME_MESSAGE);
        }
        nameParts[2] = surname;
    }

    public String getSuffix() {
        return BooleanUtils.isEmptyOrNullArray(nameParts)
                ? null :
                nameParts[3];
    }

    public void setSuffix(String suffix) {
        nameParts[3] = suffix;
    }

    public String[] getPostNominals() {
        return BooleanUtils.isEmptyOrNullArray(postNominals)
                ? null
                : postNominals;
    }

    public void setPostNominals(String[] postNominals) {
        this.postNominals = postNominals;
    }

    // returns full name as a string with post-nominals (e.g., PhD, M.Arch, etc)
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();

        // Directly use the nameParts array
        if (!BooleanUtils.isEmptyOrNullArray(nameParts)) {
            for (String namePart : nameParts) {
                if (BooleanUtils.isBlankOrEmptyString(namePart)) {
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
        return BooleanUtils.isEmptyOrNullArray(addresses)
                ? null
                : addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public String[] getPhoneNumbers() {
        return BooleanUtils.isEmptyOrNullArray(phoneNumbers)
                ? null
                : phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getEmails() {
        return BooleanUtils.isEmptyOrNullArray(emails)
                ? null
                : emails;
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
                builder.append(fieldName).append("=").append(Arrays.toString((Object[]) fieldValue)).append(", ");
            }
        }

        if (builder.length() > (className + "{").length()) {
            builder.setLength(builder.length() - 2);
        }

        builder.append("}");
        return builder.toString();
    }
}
