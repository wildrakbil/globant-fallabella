package com.presidential.election.backend.controller;

import com.presidential.election.backend.model.dao.IUserDAO;
import com.presidential.election.backend.model.entity.User;
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
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private IUserDAO userDao;

    private ModelMapper modelMapper = new ModelMapper();
    private Map<String, Object> response = new HashMap<>();

    @GetMapping("/user")
    public ResponseEntity<?> listUsers() {
        try {
            response.put("data", userDao.findAll());
        } catch (DataAccessException e) {
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
                response.put("errors", errors);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("user", userDao.save(modelMapper.map(user, User.class)));
        } catch (DataAccessException e) {
            response.put("message", String.format("Error creating user :: %s", user.getName()));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if (!userDao.findById(id).isEmpty()) {
                userDao.deleteById(id);
                response.put("message", String.format("el ususario con Id:%d fue borrado", id));
            }
        } catch (DataAccessException e) {
            response.put("message", String.format("Error borrando el ususario con Id: %s", id));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDTO user, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Optional<User> userActual = userDao.findById(id);
            userActual.ifPresent(u -> {
                user.setId(u.getId());
                response.put("message", userDao.save(modelMapper.map(user, User.class)));
            });

        } catch (DataAccessException e) {
            response.put("message", String.format("Error al actualizar el usuario %s en la base de datos", user.getName()));
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
