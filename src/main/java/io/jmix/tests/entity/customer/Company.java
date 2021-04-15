package io.jmix.tests.entity.customer;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "COMPANY")
@JmixEntity
@Entity(name = "Company")
public class Company extends Customer {

    @Column(name = "REGISTRATION_ID")
    private String registrationId;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "COMPANY_TYPE")
    private String companyType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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