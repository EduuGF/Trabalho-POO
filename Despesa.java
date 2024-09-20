import java.time.LocalDate;

public class Despesa {
    private String descricao;
    private double valor;
    private LocalDate dataVencimento;
    private String categoria;
    private boolean paga;
    private double valorPendente;
    private LocalDate dataPag;

    // construtor despesa
    public Despesa(String descricao, double valor, LocalDate dataVencimento, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.categoria = categoria;
        this.paga = false;
        this.valorPendente = valor;
        this.dataPag = null;
    }

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isPaga() {
        return paga;
    }

    public double getValorPendente() {
        return valorPendente;
    }

    public LocalDate getDataPag() {
        return dataPag;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public void setValorPendente(double valorPendente) {
        this.valorPendente = valorPendente;
    }

    public void registrarPagamento(double valorPago, LocalDate dataPag) {
        if (valorPago > valorPendente) {
            throw new IllegalArgumentException("O valor pago não pode ser maior que o valor pendente.");
        }

        valorPendente -= valorPago;

        if (valorPendente == 0) {
            paga = true;
            this.dataPag = dataPag;
        }
    }

    @Override
    public String toString() {
        String statusPagamento = valorPendente <= 0 ? "Sim" : "Não";

        return "Despesa: " + descricao +
                ", Valor: " + valor +
                ", Data de Vencimento: " + dataVencimento +
                ", Categoria: " + categoria +
                ", Paga: " + statusPagamento +
                ", Valor Pendente: " + valorPendente +
                (statusPagamento.equals("Sim") ? ", Data de Pagamento: " + dataPag : "");
    }

}