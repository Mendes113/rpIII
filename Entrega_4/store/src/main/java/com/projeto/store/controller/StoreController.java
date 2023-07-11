package com.projeto.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class StoreController {
    


    
    @GetMapping(value="/store")
    public String getStore(Model model) {
        
        return "store";
    }
}

