package com.presidential.election;

import com.presidential.election.backend.controller.UsersController;
import com.presidential.election.backend.model.dao.IUserDAO;
import com.presidential.election.backend.model.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;

    @MockBean
    private IUserDAO iUserDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersControllerTest.class);
    private PodamFactory factory = new PodamFactoryImpl();


    @Test
    public void contextLoads() throws Exception {
        assertThat(usersController).isNotNull();
    }

    @Test
    public void listUsersOK() throws Exception {
        LOGGER.info("Executing the test... listUsersOK");
        List<User> users = new ArrayList<>();
        users.add(factory.manufacturePojo(User.class));
        Mockito.when(iUserDAO.findAll()).thenReturn(users);
        ResponseEntity<?> responseEntity = usersController.listUsers();
        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        Assert.assertNotNull(responseEntity);
        Map<String, Object> objects = ((Map<String, Object>) responseEntity.getBody());
        List<User> usersOut = (List<User>) objects.get("data");
        usersOut.forEach(uOut->{
            Assert.assertEquals(uOut.getId(),users.get(0).getId());
            Assert.assertEquals(uOut.getDocumentNumber(),users.get(0).getDocumentNumber());
            Assert.assertEquals(uOut.getName(),users.get(0).getName());
            Assert.assertEquals(uOut.getBirthday(),users.get(0).getBirthday());
            Assert.assertEquals(uOut.getSex(),users.get(0).getSex());
            Assert.assertEquals(uOut.getSurname(),users.get(0).getSurname());
        });
    }

}
