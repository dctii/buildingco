package com.solvd.buildingco.stakeholders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer extends Stakeholder {

    public Customer(String[] nameParts, String[] postNominals, String[] organizationNames,
                    String[] roles, String[] addresses, String[] phoneNumbers, String[] emails) {
        super(nameParts, postNominals, organizationNames, roles, addresses, phoneNumbers, emails);
    }

    private void ensureCustomerRole() {
        String[] currentRoles = getRoles();
        List<String> roleList = new ArrayList<>();

        if (currentRoles != null) {
            roleList.addAll(Arrays.asList(currentRoles));
        }

        if (!roleList.contains("Customer")) {
            roleList.add("Customer");
        }

        setRoles(roleList.toArray(new String[0]));
    }
}
