package atividade_1.com.locadora;

import atividade_1.com.locadora.model.Locadora;
import atividade_1.com.locadora.model.veiculos.Carro;
import atividade_1.com.locadora.model.veiculos.Moto;

public class Main {
    public static void main(String[] args) {
        Locadora locadora = new Locadora("Locadora XPTO");

        Carro carro1 = new Carro("Civic", 2022, "ABC1234", 4);
        Moto moto1 = new Moto("XRE 300", 2021, "XYZ9876", 300);

        locadora.cadastrarVeiculo(carro1);
        locadora.cadastrarVeiculo(moto1);

        locadora.listarVeiculos();

        double valor = locadora.calcularValorTotalLocacao("ABC1234", 3);
        if (valor >= 0) {
            System.out.println("Valor da locação: R$ " + valor);
        }
    }
}
