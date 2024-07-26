package br.com.loja.jogos.lojajogos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JogoController {


    @GetMapping("/index")
    public String mostrarTemplate(Model model){
        model.addAttribute("titulo", "Meu Template");
        return "index";
    }
}
