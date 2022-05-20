package com.presidential.election.backend.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CANDIDATURES")
public class Candidacy implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CA_ID")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "US_ID",unique = true)
    private User idUser;


    @Column(name = "CA_PROPOSAL", nullable = false, length = 200)
    private String proposal;

    @Column(name = "CA_PARTY", nullable = false, length = 20)
    private String party;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
