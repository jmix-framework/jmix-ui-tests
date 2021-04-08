package io.jmix.tests.entity.customer;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum CustomerGrade implements EnumClass<String> {

    BRONZE("bronze grade"),
    SILVER("silver grade"),
    GOLD("gold grade");

    private String id;

    CustomerGrade(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CustomerGrade fromId(String id) {
        for (CustomerGrade at : CustomerGrade.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}