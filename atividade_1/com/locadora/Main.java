package atividade_1.com.locadora;

import atividade_1.com.locadora.model.veiculos.Carro;
import atividade_1.com.locadora.model.veiculos.Moto;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n\nSistema de Locadora iniciado!");

        Carro carro = new Carro("Fiat Uno", 2020, "KGA 2209", 4);
        Moto moto = new Moto("Honda CG 160", 2024, "KLG 9932", 160);

        carro.exibirDetalhes();
        moto.exibirDetalhes();
        
    }
}
