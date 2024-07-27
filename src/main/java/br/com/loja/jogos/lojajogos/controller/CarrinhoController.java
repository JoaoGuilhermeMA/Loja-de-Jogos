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

import java.util.HashMap;
import java.util.Map;
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

            Map<Jogo, Integer> carrinho = (Map<Jogo, Integer>) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new HashMap<>();
                session.setAttribute("carrinho", carrinho);
            }

            carrinho.put(jogo, carrinho.getOrDefault(jogo, 0) + 1);
            session.setAttribute("carrinho", carrinho);
        }

        return "redirect:/index";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Recupera o carrinho da sessão
        Map<Jogo, Integer> carrinho = (Map<Jogo, Integer>) session.getAttribute("carrinho");

        // Verifica se o carrinho é nulo ou está vazio
        if (carrinho == null || carrinho.isEmpty()) {
            // Adiciona uma mensagem de aviso
            redirectAttributes.addFlashAttribute("aviso", "Seu carrinho está vazio.");
            // Redireciona para a página inicial
            return "redirect:/index";
        }

        // Adiciona o carrinho ao modelo
        model.addAttribute("carrinho", carrinho);
        return "verCarrinho";
    }

    // Método para remover um item do carrinho
    @GetMapping("/removerCarrinho")
    public String removerCarrinho(@RequestParam("id") Long id, HttpSession session) {
        // Recupera o carrinho da sessão
        Map<Jogo, Integer> carrinho = (Map<Jogo, Integer>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new HashMap<>();
        }

        // Encontra e remove o item com o ID especificado
        carrinho.entrySet().removeIf(entry -> entry.getKey().getId().equals(id));

        // Atualiza o carrinho na sessão
        session.setAttribute("carrinho", carrinho);

        return "redirect:/verCarrinho";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarComprar(HttpSession session){
        // Invalida a sessão existente
        session.invalidate();

        // Redireciona para a página index
        return "redirect:/index";
    }

}
