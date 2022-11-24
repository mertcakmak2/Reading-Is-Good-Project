package com.project.readingisgood.unit_tests.auth;

import com.project.readingisgood.controller.AuthController;
import com.project.readingisgood.model.request.LoginRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerUnitTests {

    @MockBean
    AuthController authController;

    @Test
    void login() {
        LoginRequestModel loginRequestModel = new LoginRequestModel(
                "mertcakmak2@gmail.com",
                "password"
        );
        var dataResult = new SuccessDataResult<String>("Signed in.", "jwt-token");
        var expectedResponse = new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);

        Mockito.when(authController.login(loginRequestModel)).thenReturn(expectedResponse);
        var response = authController.login(loginRequestModel);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
}
