package com.presidential.election.backend.controller;

import com.presidential.election.backend.model.dao.ICandidacyDAO;
import com.presidential.election.backend.model.dao.IUserDAO;
import com.presidential.election.backend.model.dao.IVoteDAO;
import com.presidential.election.backend.model.entity.Candidacy;
import com.presidential.election.backend.model.entity.User;
import com.presidential.election.backend.model.entity.Vote;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/colombia")
public class VotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotesController.class);

    @Autowired
    private IVoteDAO voteDao;

    @Autowired
    private IUserDAO userDao;

    @Autowired
    private ICandidacyDAO candidacyDAO;

    private ModelMapper modelMapper = new ModelMapper();
    private Map<String, Object> response = new HashMap<>();


    @PostMapping("/vote/{idUser}/{idCandidacy}")
    public ResponseEntity<?> vote(@PathVariable Long idUser, @PathVariable Long idCandidacy) {
        try {
            AtomicBoolean yavote = new AtomicBoolean(false);
            Vote vote = new Vote();
            Optional<User> user = userDao.findById(idUser);
            user.ifPresent(u -> {
                vote.setIdUser(u);
                Vote voteActual = voteDao.findByIdUser(u);
                if (voteActual != null) {
                    response.put("menssage", String.format("EL usuario %s ya voto", idUser));
                    yavote.set(true);
                }
            });
            if (!yavote.get()) {
                Optional<Candidacy> candidacy = candidacyDAO.findById(idCandidacy);
                candidacy.ifPresent(ca -> {
                    vote.setIdCandidacy(new Candidacy());
                    vote.setIdCandidacy(ca);
                });
                Long cuenta = 0l;
                cuenta += vote.getCount() == null ? 0 : vote.getCount();
                vote.setCount(cuenta + 1);
                response.put("vote", voteDao.save(vote));
            }
        } catch (DataAccessException e) {
            response.put("message", String.format("Error creating el voto del ususario %s", idUser));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
