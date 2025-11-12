package com.exercise.tal.oligarch.rating.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Oligarch {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal assetsValue;

    public Oligarch() {}

    public Oligarch(Long id, String firstName, String lastName, BigDecimal assetsValue) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assetsValue = assetsValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(BigDecimal assetsValue) {
        this.assetsValue = assetsValue;
    }
}
