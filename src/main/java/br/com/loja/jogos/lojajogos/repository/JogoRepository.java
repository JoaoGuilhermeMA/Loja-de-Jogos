package br.com.loja.jogos.lojajogos.repository;

import br.com.loja.jogos.lojajogos.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    List<Jogo> findByIsDeletedIsNull();
}
