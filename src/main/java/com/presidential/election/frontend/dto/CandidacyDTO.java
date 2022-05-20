package com.presidential.election.frontend.dto;

import org.junit.platform.commons.util.ToStringBuilder;

import java.io.Serializable;

public class CandidacyDTO implements Serializable {

    private static final long serialVersionUID = 2L;

    private Long id;
    private UserDTO idUser;
    private String proposal;
    private String party;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdUser(UserDTO idUser) {
        this.idUser = idUser;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getIdUser() {
        return idUser;
    }

    public String getProposal() {
        return proposal;
    }

    public String getParty() {
        return party;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(UserDTO.class)
                .append("id", id)
                .append("idUser", idUser)
                .append("proposal", proposal)
                .append("party", party)
                .toString();
    }
}
