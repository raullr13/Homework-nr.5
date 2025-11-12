package controller;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import model.User;
import model.validation.UserValidator;
import service.user.AuthenticationService;
import view.LoginView;

import javax.management.Notification;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authentificationService;
    private final UserValidator userValidator;

    public LoginController(LoginView loginView, AuthenticationService authentificationService, UserValidator userValidator) {
        this.loginView = loginView;
        this.authentificationService = authentificationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authentificationService.login(username, password);

            if(user == null)
            {
                loginView.setActionTargetText("Invalid username or password");
            }
            else
            {
                loginView.setActionTargetText("Login successful!");
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();

            if(errors.isEmpty())
            {
                if(authentificationService.register(username, password))
                {
                    loginView.setActionTargetText("Register successful!");
                }
                else
                {
                    loginView.setActionTargetText("Register failed!");
                }
            }
            else
            {
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }
        }
    }

}
