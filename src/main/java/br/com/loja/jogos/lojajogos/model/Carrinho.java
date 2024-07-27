package br.com.loja.jogos.lojajogos.model;

import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private Map<Long, Integer> itens = new HashMap<>();

    public void adicionarItem(Jogo jogo) {
        itens.put(jogo.getId(), itens.getOrDefault(jogo.getId(), 0) + 1);
    }

    public void removerItem(Long id) {
        if (itens.containsKey(id)) {
            int quantidade = itens.get(id);
            if (quantidade > 1) {
                itens.put(id, quantidade - 1);
            } else {
                itens.remove(id);
            }
        }
    }

    public Map<Long, Integer> getItens() {
        return itens;
    }

    public int getQuantidadeTotal() {
        return itens.values().stream().mapToInt(Integer::intValue).sum();
    }


}
