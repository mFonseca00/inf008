package atividade_1.com.locadora.model;

import java.util.ArrayList;

import atividade_1.com.locadora.model.veiculos.Veiculo;

public class Locadora {
    private String nome;
    private ArrayList<Veiculo> veiculos;

    public void cadastrarVeiculo(Veiculo v){
        veiculos.add(v);
    }

    public void listarVeiculos(){
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
    
        for (Veiculo v : veiculos) {
            v.exibirDetalhes();
        }
    }

    public double calcularValorTotalLocacao(String placa, int dias){
        for(Veiculo v : veiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){
                double valor = v.calcularDiaria(dias);
                return valor;
            }
        }
        System.out.println("Veículo não cadastrado");
        return -1;
    }

    public Locadora(String nome){
        this.nome = nome;
        this.veiculos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    
}
