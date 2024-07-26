package br.com.loja.jogos.lojajogos.controller;

import br.com.loja.jogos.lojajogos.model.Jogo;
import br.com.loja.jogos.lojajogos.service.JogoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class JogoController {

    @Autowired
    JogoService jogoService;

    @GetMapping
    public String index(Model model, HttpServletResponse response, @CookieValue(value = "visita", defaultValue = "none") String visitaCookie) {
        // Retrieve non-deleted games
        List<Jogo> jogos = jogoService.findByIsDeletedIsFalse();
        model.addAttribute("jogos", jogos);

        // Add cookie
        if ("none".equals(visitaCookie)) {
            Cookie cookie = new Cookie("visita", LocalDateTime.now().toString());
            cookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(cookie);
        }
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Jogo> jogos = jogoService.findByIsDeletedIsFalse();
        model.addAttribute("jogos", jogos);
        return "admin";
    }

    @GetMapping("/cadastro")
    public String cadastroJogo(Model model) {
        model.addAttribute("jogo", new Jogo());
        return "cadastroJogo"; // Nome do arquivo HTML
    }

    @PostMapping("/salvar")
    public ModelAndView  salvarJogo(@ModelAttribute Jogo jogo, @RequestParam("file") MultipartFile file){
        jogo.setImageUri(file.getOriginalFilename());
        jogoService.create(jogo);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        modelAndView.addObject("jogo", jogoService.findByIsDeletedIsFalse());

        return modelAndView;
    }

}
