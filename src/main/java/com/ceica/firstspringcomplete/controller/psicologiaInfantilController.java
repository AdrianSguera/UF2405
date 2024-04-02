package com.ceica.firstspringcomplete.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class psicologiaInfantilController {

    @GetMapping("/psicologiaInfantil")
    public String getPsicologiaInfantil(){
        return "psicologiaInfantil";
    }
}
