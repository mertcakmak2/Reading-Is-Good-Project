package com.project.readingisgood.unit_tests.auth;

import com.project.readingisgood.model.request.LoginRequestModel;
import com.project.readingisgood.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceUnitTests {

    @MockBean
    AuthService authService;

    @Test
    void login_test_method_should_return_jwt_string(){
        LoginRequestModel loginRequestModel = new LoginRequestModel(
                "mertcakmak2@gmail.com",
                "password"
        );
        Mockito.when(authService.login(loginRequestModel)).thenReturn("jwt-token");
        String jwt = authService.login(loginRequestModel);

        assertNotNull(jwt);
    }

}
