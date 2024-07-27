package br.com.loja.jogos.lojajogos.service;

import br.com.loja.jogos.lojajogos.model.Jogo;
import br.com.loja.jogos.lojajogos.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    /**
     * Metodo para pegar todos os jogos do banco de dados
     * @return Retorna a lista de todos os jogos que tem no banco de dados
     */
    public List<Jogo> findAll(){
        return jogoRepository.findAll();
    }

    public List<Jogo> findByIsDeletedIsNull(){
        return jogoRepository.findByIsDeletedIsNull();
    }

    /**
     * Metodo para buscar um jogo no banco de dados, de acordo com o id fornecido
     * @param id id que vai ser buscado no banco de dados
     * @return retorna o jogo com base no id passado
     */
    public Optional<Jogo> findById(Long id) {
        return jogoRepository.findById(id);
    }

    /**
     * Deleta um jogo do banco de dados baseado no seu id
     * @param id id do jogo que vai ser deletado do banco de dados
     */
    public void deleteById(Long id) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isPresent()) {
            Jogo jogo = jogoOpt.get();
            jogo.setIsDeleted(Instant.now().toEpochMilli());
            jogoRepository.save(jogo);
        }
    }

    /**
     * Metodo para salvar um jogo no banco de dados
     * @param jogo É o jogo que vai ser salvo no banco de dados
     * @return retorna o jogo que foi salvo no banco de dados
     */
    public Jogo create(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    /**
     * Metodo para atualizar um jogo que está no banco de dados
     * @param jogo Jogo que vai ser atualizado
     * @return retorna o jogo atualizado
     */
    public Jogo update(Jogo jogo){
        System.out.println("========= " + jogo.getNome());
        System.out.println("========= " + jogo.getId());
        return jogoRepository.saveAndFlush(jogo);
    }

}
