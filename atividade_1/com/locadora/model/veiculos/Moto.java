package atividade_1.com.locadora.model.veiculos;

public class Moto extends Veiculo{

    private int cilindradas;

    @Override
    public void exibirDetalhes(){
        System.out.println("\n-----------------" + " Moto " + "------------------\n");
        super.exibirDetalhes();
        System.out.println("\t| Cilindradas: " + cilindradas);
    }

    @Override
    public double calcularDiaria(int dias){
        return dias*50 + cilindradas*0.1;
    }

    public Moto(String modelo, int ano, String placa, int cilindradas) {
        super(modelo, ano, placa);
        this.cilindradas = cilindradas;
    }

    public Moto(String modelo, String placa){
        super(modelo,placa);
        this.cilindradas = 150;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }
    
}
