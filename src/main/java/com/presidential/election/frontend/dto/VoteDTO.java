package com.presidential.election.frontend.dto;

import org.junit.platform.commons.util.ToStringBuilder;

import java.io.Serializable;

public class VoteDTO implements Serializable {

    private static final long serialVersionUID = 3L;
    private Long id;
    private UserDTO idUser;
    private CandidacyDTO idCandidacy;
    private Long count;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDTO idUser) {
        this.idUser = idUser;
    }

    public CandidacyDTO getIdCandidacy() {
        return idCandidacy;
    }

    public void setIdCandidacy(CandidacyDTO idCandidacy) {
        this.idCandidacy = idCandidacy;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(UserDTO.class)
                .append("id", id)
                .append("idUser", idUser)
                .append("idCandidacy", idCandidacy)
                .append("count", count)
                .toString();
    }

}
