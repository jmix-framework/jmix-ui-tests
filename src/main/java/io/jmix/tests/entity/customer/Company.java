package io.jmix.tests.entity.customer;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "CUSTOMER")
@JmixEntity
@Entity(name = "Company")
public class Company extends Customer {

    @Column(name = "REGISTRATION_ID")
    private String registrationId;

    @Column(name = "COMPANY_TYPE")
    private String companyType;

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

}