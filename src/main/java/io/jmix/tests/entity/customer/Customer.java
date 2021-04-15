package io.jmix.tests.entity.customer;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "CUSTOMER")
@Entity(name = "Customer")
@JmixEntity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Email
    @Column(name = "EMAIL")
    private String email;

    @JmixProperty(mandatory = true)
    @NotNull
    @Column(name = "GRADE", nullable = false)
    private String grade;

    public CustomerGrade getGrade() {
        return grade == null ? null : CustomerGrade.fromId(grade);
    }

    public void setGrade(CustomerGrade grade) {
        this.grade = grade == null ? null : grade.getId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}