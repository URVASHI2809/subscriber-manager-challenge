package com.interview.subscribermanager.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String msisdn;

    private String planCode;

    private Date activatedOn;

    public Subscriber() {
    }

    public Subscriber(String msisdn, String planCode, Date activatedOn) {
        this.msisdn = msisdn;
        this.planCode = planCode;
        this.activatedOn = activatedOn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    @Override
    public String toString() {
        return "Subscriber{id=" + id + ", msisdn='" + msisdn + "', planCode='" + planCode
                + "', activatedOn=" + activatedOn + "}";
    }
}
