package atividade_1.com.locadora.model.veiculos;

public class Veiculo {
    private String placa;
    private String modelo;
    private int ano;
    static int totalVeiculos;

    public void exibirDetalhes(){
        System.out.println("\t| Modelo: " + this.modelo);
        System.out.println("\t| Ano: " + this.ano);
        System.out.println("\t| Placa: " + this.placa);
    }

    public double calcularDiaria(int dias){
        return 0;
    }

    public Veiculo(String modelo, int ano, String placa){
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        totalVeiculos++;
    }

    public Veiculo(String modelo, String placa){
        this.placa = placa;
        this.modelo = modelo;
        ano = 2000;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public static int getTotalVeiculos() {
        return totalVeiculos;
    }
    
}
