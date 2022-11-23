package com.project.readingisgood.controller;

import com.project.readingisgood.model.request.LoginRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Login.")
    public ResponseEntity<DataResult> login(@RequestBody @Valid LoginRequestModel loginRequestModel) {
        var dataResult = new SuccessDataResult<String>("Signed in.", authService.login(loginRequestModel));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }
}
