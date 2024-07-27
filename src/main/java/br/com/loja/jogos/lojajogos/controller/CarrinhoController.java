package br.com.loja.jogos.lojajogos.controller;

import br.com.loja.jogos.lojajogos.model.Jogo;
import br.com.loja.jogos.lojajogos.service.JogoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String verCarrinho(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Recupera o carrinho da sessão
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");

        // Verifica se o carrinho é nulo ou está vazio
        if (carrinho == null || carrinho.isEmpty()) {
            // Adiciona uma mensagem de aviso
            redirectAttributes.addFlashAttribute("aviso", "Seu carrinho está vazio.");
            // Redireciona para a página inicial
            return "redirect:/";
        }

        // Adiciona o carrinho ao modelo
        model.addAttribute("carrinho", carrinho);
        return "verCarrinho";
    }

    // Método para remover um item do carrinho
    @GetMapping("/removerCarrinho")
    public String removerCarrinho(@RequestParam("id") Long id, HttpSession session) {
        // Recupera o carrinho da sessão
        List<Jogo> carrinho = (List<Jogo>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        // Encontra e remove o item com o ID especificado
        carrinho.removeIf(jogo -> jogo.getId() == id );

        // Atualiza o carrinho na sessão
        session.setAttribute("carrinho", carrinho);

        return "redirect:/";
    }

}
