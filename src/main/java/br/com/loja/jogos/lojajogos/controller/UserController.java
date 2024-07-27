package br.com.loja.jogos.lojajogos.controller;

import br.com.loja.jogos.lojajogos.model.Usuario;
import br.com.loja.jogos.lojajogos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadusuario")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("cadusuario");
    }

    @PostMapping("/salvarUsuario")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(defaultValue = "false") boolean isAdmin) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(isAdmin);
        System.out.println("oi");
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAdmin(isAdmin);
        userRepository.save(user);
        return "redirect:/login";
    }


}
