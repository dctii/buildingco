package com.solvd.buildingco.stakeholders;

public interface PersonalName {
    String[] getNameParts();
    String getForename();
    String getMiddleName();
    String getSurname();
    String getSuffix();
    String[] getPostNominals();
    String getFullName();
}
