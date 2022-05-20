package com.presidential.election.backend.model.entity;

import javax.persistence.Table;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "VOTES")
public class Vote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VO_ID")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "US_ID",unique = true)
    private User idUser;

    @ManyToOne()
    @JoinColumn(name = "CA_ID")
    private Candidacy idCandidacy;

    @Column(name = "VO_COUNT")
    private Long count;

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

    public Candidacy getIdCandidacy() {
        return idCandidacy;
    }

    public void setIdCandidacy(Candidacy idCandidacy) {
        this.idCandidacy = idCandidacy;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
