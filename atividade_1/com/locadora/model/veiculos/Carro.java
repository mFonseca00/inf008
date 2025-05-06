package atividade_1.com.locadora.model.veiculos;

public class Carro extends Veiculo{
    
    private int quantidadePortas;

    @Override
    public void exibirDetalhes(){
        System.out.println("\n-----------------" + " Carro " + "-----------------\n");
        super.exibirDetalhes();
        System.out.println("\t| Número de portas:" + quantidadePortas);
    }

    @Override
    public double calcularDiaria(int dias){
        return dias*100 + quantidadePortas*20;
    }

    public Carro(String modelo, int ano, String placa, int quantidadePortas) {
        super(modelo, ano, placa);
        this.quantidadePortas = quantidadePortas;
    }

    public Carro(String modelo, String placa){
        super(modelo,placa);
        this.quantidadePortas = 2;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

}
