package br.com.loja.jogos.lojajogos.controller;

import br.com.loja.jogos.lojajogos.model.Jogo;
import br.com.loja.jogos.lojajogos.service.JogoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoController {

    @Autowired
    private JogoService jogoService;

    @GetMapping("/adicionarCarrinho")
    public String adicionarCarrinho(@RequestParam("id") Long id, HttpSession session) {
        Optional<Jogo> jogoOptional = jogoService.findById(id);

        if (jogoOptional.isPresent()) {
            Jogo jogo = jogoOptional.get();

            List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new ArrayList<>();
                session.setAttribute("carrinho", carrinho);
            }

            carrinho.add(jogo);
            session.setAttribute("carrinho", carrinho);
        }

        return "redirect:/";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model, HttpSession session) {
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        model.addAttribute("carrinho", carrinho);
        return "verCarrinho";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Long id, Model model, HttpSession session) {
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");

        if (carrinho != null) {
            Optional<Jogo> jogoOptional = jogoService.findById(id);
            if (jogoOptional.isPresent()) {
                Jogo jogo = jogoOptional.get();
                model.addAttribute("jogo", jogo);
                return "editarJogo"; // Página para editar o jogo
            }
        }

        return "redirect:/verCarrinho";
    }

    @GetMapping("/deletar")
    public String deletar(@RequestParam("id") Long id, HttpSession session) {
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");

        if (carrinho != null) {
            carrinho.removeIf(jogo -> jogo.getId() ==id);
            session.setAttribute("carrinho", carrinho);
        }

        return "redirect:/verCarrinho";
    }
}
