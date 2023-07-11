package com.projeto.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projeto.store.model.User;
import com.projeto.store.model.UserSession;
import com.projeto.store.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HelloController {

    private final UserService userService;
    private final JogoController jogoController;
    public HelloController(UserService userService, JogoController jogoController) {
        this.userService = userService;
        this.jogoController = jogoController;
    }

    @GetMapping("/login")
    public String getIndex() {
        return "index";
    }

@PostMapping("/login")
public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
    if (userService.authenticateUser(email, password)) {
        // Authentication successful, store the user in the session
        User user = userService.getUserByEmail(email);
        session.setAttribute("currentUser", user);
        
        // Redirect to the home page or another desired page
        return jogoController.getJogosPage(model);
    } else {
        // Authentication failed, add an error message to the model and return to the login page
        model.addAttribute("error", "Invalid credentials");
        return "index";
    }
}

   

}
