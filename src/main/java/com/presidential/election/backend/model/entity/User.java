package com.presidential.election.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USERS")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "US_ID")
    private Long id;

    @Column(name = "US_NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "US_SURNAME", nullable = false, length = 50)
    private String surname;

    @Column(name = "US_BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "US_SEX", length = 10)
    private String sex;

    @Column(name = "US_DOCUMENT_NUMBER",unique = true, nullable = false)
    private Long documentNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }
}
