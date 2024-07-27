package br.com.loja.jogos.lojajogos.repository;

import br.com.loja.jogos.lojajogos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long>  {
    Optional<Usuario> findUsuarioByUsername(String username);
}
