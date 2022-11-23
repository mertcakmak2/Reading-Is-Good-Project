package com.project.readingisgood.service.auth;

import com.project.readingisgood.model.request.LoginRequestModel;

public interface AuthService {

    String login(LoginRequestModel loginRequestModel);
}
