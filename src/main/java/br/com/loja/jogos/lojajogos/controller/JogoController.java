package br.com.loja.jogos.lojajogos.controller;

import br.com.loja.jogos.lojajogos.model.Jogo;
import br.com.loja.jogos.lojajogos.service.JogoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JogoController {

    @Autowired
    JogoService jogoService;

    @GetMapping
    public String index(Model model, HttpServletResponse response, @CookieValue(value = "visita", defaultValue = "none") String visitaCookie, HttpSession session) {
        // Retrieve non-deleted games
        List<Jogo> jogos = jogoService.findByIsDeletedIsNull();
        model.addAttribute("jogos", jogos);

        // Add cookie
        if ("none".equals(visitaCookie)) {
            Cookie cookie = new Cookie("visita", LocalDateTime.now().toString());
            cookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(cookie);
        }

        // Get the cart from the session
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        // Add the cart item count to the model
        model.addAttribute("quantidadeCarrinho", carrinho.size());

        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Jogo> jogos = jogoService.findByIsDeletedIsNull();
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
        modelAndView.addObject("jogo", jogoService.findByIsDeletedIsNull());
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");


        return modelAndView;
    }
    @GetMapping("/editPage/{id}")
    public ModelAndView getEditPage(@PathVariable Long id){

        Optional<Jogo> j = jogoService.findById(id);
        if (j.isPresent()){
            ModelAndView mv = new ModelAndView("editPage");
            mv.addObject("jogo", j.get());
            System.out.println("Estou dentro do getEditPag e o meu jogo é" + j.get().getNome());
            return mv;
        }else{
            return new ModelAndView("redirect:/admin");
        }
    }

    @GetMapping("/deletar")
    public String deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        // Busca o jogo pelo ID
        Optional<Jogo> jogoOptional = jogoService.findById(id);
        if (jogoOptional.isPresent()) {
            jogoService.deleteById(id);

            // Adiciona uma mensagem de sucesso
            redirectAttributes.addFlashAttribute("mensagem", "Remoção ocorrida com sucesso.");
        } else {
            // Adiciona uma mensagem de erro se o item não for encontrado
            redirectAttributes.addFlashAttribute("mensagem", "Item não encontrado.");
        }
        // Redireciona para a página de índice
        return "redirect:/admin";
    }

}
