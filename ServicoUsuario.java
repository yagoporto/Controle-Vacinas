package br.com.api;

import java.util.ArrayList;

import br.com.api.Vacinas.PublicoAlvo;


public class ServicoUsuario {
    private ArrayList<Vacinas> vacinas = new ArrayList<>();
    private int contadorId = 1;

    public ArrayList<Vacinas> consultarVacinas() {
        return vacinas;
    }


    public Vacinas adicionar(String vacina, int limiteAplicacao, PublicoAlvo publicoAlvo ) {
        Vacinas usuario = new Vacinas(String.valueOf(contadorId++), vacina, limiteAplicacao, publicoAlvo );
        vacinas.add(usuario);
        return usuario;
    }

public Vacinas consultarPorFaixa(PublicoAlvo publicoAlvo){
    for(Vacinas vacina : vacinas){
        if (vacina.getPublicoAlvo().equals(publicoAlvo) ) {
            return vacina;
        
    }
}
return null;


}
public Vacinas consultarVacinasRecomendadas(int limiteAplicacao){
    for(Vacinas vacina :vacinas){
        if (vacina.getlimiteAplicacao() == (limiteAplicacao)) {
            return vacina;
        }
    }
    return null;
}
   
public Vacinas consultarVacinasNaoAplicaveis(String id){
    for (Vacinas vacina : vacinas) {
        if (vacina.getId().equals(id)) {
            return vacina;
        }
    }
    return null;

}


}
