package com.presidential.election.backend.controller;


import com.presidential.election.backend.model.dao.ICandidacyDAO;
import com.presidential.election.backend.model.dao.IUserDAO;
import com.presidential.election.backend.model.entity.Candidacy;
import com.presidential.election.backend.model.entity.User;
import com.presidential.election.frontend.dto.CandidacyDTO;
import com.presidential.election.frontend.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colombia")
public class CandidaturesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidaturesController.class);

    @Autowired
    private IUserDAO userDao;

    @Autowired
    private ICandidacyDAO candidacyDAO;

    private ModelMapper modelMapper = new ModelMapper();
    private Map<String, Object> response = new HashMap<>();

    @PostMapping("/candidacy/{idUser}")
    public ResponseEntity<?> createCandidacy(@PathVariable Long idUser, @RequestBody CandidacyDTO candidacy, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
                response.put("errors", errors);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Optional<User> user = userDao.findById(idUser);
            user.ifPresent(u -> {
                candidacy.setIdUser(new UserDTO());
                candidacy.setIdUser(modelMapper.map(u, UserDTO.class));
                Candidacy newCandidacy = modelMapper.map(candidacy, Candidacy.class);
                response.put("Candidacy", candidacyDAO.save(newCandidacy));
            });
        } catch (DataAccessException e) {
            System.out.println(e.getCause().getCause());
            if (e.getCause().getCause().toString().contains("Duplicate")) {
                response.put("message", String.format("El candidato %d ya presento una propuesta ", idUser));
            } else {
                response.put("message", String.format("Error creating idUser %s candidacy", idUser));
            }
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/candidacy")
    public ResponseEntity<?> listCandidatures() {
        try {
            response.put("data", candidacyDAO.findAll());
        } catch (DataAccessException e) {
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/candidacy/{id}")
    public ResponseEntity<?> deleteCandidacy(@PathVariable Long id) {
        try {
            if (!candidacyDAO.findById(id).isEmpty()) {
                candidacyDAO.deleteById(id);
                response.put("message", String.format("la candidatura del  userId:%d fue borrada", id));
            }
        } catch (DataAccessException e) {
            response.put("message", String.format("la candidatura del  userId:%d", id));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/candidacy/{id}")
    public ResponseEntity<?> updateCandidacy(@PathVariable long id, @RequestBody CandidacyDTO candidacy, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Optional<Candidacy> CandidacyActual = candidacyDAO.findById(id);
            CandidacyActual.ifPresent(c -> {
                candidacy.setId(c.getId());
                candidacy.setIdUser(new UserDTO());
                candidacy.setIdUser(modelMapper.map(c.getIdUser(), UserDTO.class));
                response.put("message", candidacyDAO.save(modelMapper.map(candidacy, Candidacy.class)));
            });

        } catch (DataAccessException e) {
            response.put("message", String.format("Error al actualizar la candidatura del usuario %s en la base de datos", id));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


}
