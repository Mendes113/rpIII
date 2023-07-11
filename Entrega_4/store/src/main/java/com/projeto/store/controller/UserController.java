package com.projeto.store.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.projeto.store.model.User;
import com.projeto.store.services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final JogoController jogoController;

    @Autowired
    private final HelloController helloController;

    public UserController(JogoController jogoController, HelloController helloController) {
        this.jogoController = jogoController;
        this.helloController = helloController;
    }
    
    @Autowired
    private UserService service;
    
  @PostMapping("/register")
@ResponseStatus(HttpStatus.CREATED)
public ModelAndView registerUser(@RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("endereco") String endereco,
        @RequestParam("dob") String dob,
        @RequestParam("gender") String gender,
        @RequestParam("platform") List<String> platform,
        Model model, User user) {

    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    user.setAddres(endereco);
    user.setDob(dob);
    user.setGender(gender);
    user.setPlatform(platform);
    user.setRole("USER");

    service.createUser(user);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    return modelAndView;
}


    @GetMapping
    public List<User> getUsers(){
        return service.getAllUsers();
    }

    @GetMapping("{userId}")
    public User getUserById(@PathVariable String userId){
        return service.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable  String email){
        return service.getUserByEmail(email);
    }

    @GetMapping("/role/{role}")
    public List<User> getUserByRole(@PathVariable  String role){
        return service.getUserByRole(role);
    }

   @GetMapping("/name/{name}")
public ModelAndView getUserByName(@PathVariable String name) {
    List<User> userList = service.getUserByName(name);
    ModelAndView modelAndView;
    if (!userList.isEmpty()) {
        User user = userList.get(0);
        modelAndView = new ModelAndView("redirect:/perfil.html");
        modelAndView.addObject("user", user);
    } else {
        modelAndView = new ModelAndView("redirect:/user-not-found.html");
    }
    return modelAndView;
}



    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return service.deleteUser(userId);
    }


    
@GetMapping("/profile")
public ModelAndView showProfile(HttpSession session) {
    User user = (User) session.getAttribute("currentUser");
    
    if (user == null) {
        return new ModelAndView("redirect:/login");
    }
    if(user.getRole().equals("ADMIN")) {
        return new ModelAndView("redirect:/users/admin");
    }
    if(user.getRole().equals("publisher")) {
        return new ModelAndView("redirect:/users/publisher");
    }
    ModelAndView modelAndView = new ModelAndView("perfil");
    modelAndView.addObject("user", user);
    return modelAndView;
}

@GetMapping("/admin")
public ModelAndView showAdminProfile(HttpSession session) {
    User user = (User) session.getAttribute("currentUser");

    if (user == null) {
        return new ModelAndView("redirect:/login");
    }

    if (!user.getRole().equals("ADMIN")) {
        return new ModelAndView("redirect:/profile");
    }

    ModelAndView modelAndView = new ModelAndView("admin");
    modelAndView.addObject("user", user);
    return modelAndView;
}

@GetMapping("/publisher")
public ModelAndView showPublisherProfile(HttpSession session) {
    User user = (User) session.getAttribute("currentUser");

    if (user == null) {
        return new ModelAndView("redirect:/login");
    }

    if (!user.getRole().equals("publisher")) {
        return new ModelAndView("redirect:/profile");
    }

    ModelAndView modelAndView = new ModelAndView("publisher");
    modelAndView.addObject("user", user);
    return modelAndView;
}


@PostMapping("/delete")
public String deleteUserByEmail(@RequestParam("email") String email, Model model) {
    service.deleteUserByEmail(email);
    model.addAttribute("userDeleted", true);
    return "redirect:/users/admin";
}

@PostMapping("/updateRole")
public String updateRole(@RequestParam("email") String email, @RequestParam("role") String role, Model model) {
    User user = service.updateRole(email, role);
    if (user != null) {
        model.addAttribute("roleUpdated", true);
    } else {
        model.addAttribute("roleUpdated", false);
    }
    return "redirect:/users/admin";
}


}
