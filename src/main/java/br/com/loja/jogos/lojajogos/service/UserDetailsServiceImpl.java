package br.com.loja.jogos.lojajogos.service;

import br.com.loja.jogos.lojajogos.model.Usuario;
import br.com.loja.jogos.lojajogos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optional = repository.findUsuarioByUsername(username);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            System.out.println("Usuário encontrado: " + usuario.getUsername());
            return org.springframework.security.core.userdetails.User.withUsername(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.isAdmin() ? "ADMIN" : "USER")
                    .build();
        }
        System.out.println("Usuário não encontrado: " + username);
        throw new UsernameNotFoundException("User not found");
    }

}
