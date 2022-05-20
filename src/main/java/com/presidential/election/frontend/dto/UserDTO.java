package com.presidential.election.frontend.dto;


import org.junit.platform.commons.util.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String surname;
    private Date birthday;
    private String sex;
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

    @Override
    public String toString() {
        return new ToStringBuilder(UserDTO.class)
                .append("id", id)
                .append("name", name)
                .append("surname", surname)
                .append("birthday", birthday)
                .append("sex", sex)
                .append("documentNumber", documentNumber)
                .toString();
    }
}
