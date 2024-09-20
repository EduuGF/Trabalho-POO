import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorDespesas {
    private List<Despesa> despesas;
    private Map<Integer, Categoria> categorias; // Mapa para armazenar categorias com ID
    private int proximoIdCategoria; // Próximo ID disponível para nova categoria

    public GerenciadorDespesas() {
        this.despesas = new ArrayList<>();
        this.categorias = new HashMap<>();
        carregarDespesasDoArquivo();
        carregarCategoriasDoArquivo();
        proximoIdCategoria = categorias.isEmpty() ? 1 : categorias.keySet().stream().max(Integer::compare).get() + 1;
    }

    // Adicionar despesa e salvar no arquivo
    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
        salvarDespesaEmArquivo(despesa);
    }

    // Registrar pagamento e salvar no arquivo
    public void registrarPagamento(int indice, double valorPago, LocalDate dataPag) {
        if (indice >= 0 && indice < despesas.size()) {
            Despesa despesa = despesas.get(indice);

            // se o valor for maior ele nao deixa
            if (valorPago > despesa.getValorPendente()) {
                System.out.println("Erro: O valor pago não pode ser maior que o valor pendente.");
                return;
            }
            // registra o valor pago
            despesa.registrarPagamento(valorPago, dataPag);
            System.out.println("Pagamento de " + valorPago + " registrado para a despesa: " + despesa.getDescricao());

            // Reescrever todas as despesas no arquivo
            salvarDespesasEmArquivo();
        } else {
            System.out.println("Índice de despesa inválido!");
        }
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    // Salvar despesa nova no arquivo
    private void salvarDespesaEmArquivo(Despesa despesa) {
        try (FileWriter writer = new FileWriter("despesas.txt", true)) {
            writer.write(formatarDespesaParaArquivo(despesa) + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesa: " + e.getMessage());
        }
    }

    // Salvar todas as despesas no arquivo
    private void salvarDespesasEmArquivo() {
        try (FileWriter writer = new FileWriter("despesas.txt")) {
            for (Despesa despesa : despesas) {
                writer.write(formatarDespesaParaArquivo(despesa) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }

    // Método que formata a despesa para salvar no arquivo
    private String formatarDespesaParaArquivo(Despesa despesa) {
        return despesa.getDescricao() + "," +
                despesa.getValor() + "," +
                despesa.getDataVencimento() + "," +
                despesa.getCategoria() + "," +
                (despesa.isPaga() ? "true" : "false") + "," + // Corrigir o formato booleano para salvar corretamente
                despesa.getValorPendente() + "," +
                (despesa.getDataPag() != null ? despesa.getDataPag().toString() : ""); // Certifique-se de que a data é
                                                                                       // salva corretamente
    }

    // Carregar as despesas do arquivo
    private void carregarDespesasDoArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("despesas.txt"))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");

                if (campos.length >= 6) {
                    String descricao = campos[0];
                    double valor = Double.parseDouble(campos[1]);
                    LocalDate dataVencimento = LocalDate.parse(campos[2]);
                    String categoria = campos[3];
                    boolean paga = campos[4].equals("true"); // Certifique-se de que o boolean está correto
                    double valorPendente = Double.parseDouble(campos[5]);

                    // Leitura da data de pagamento, caso exista
                    LocalDate dataPag = (campos.length > 6 && campos[6].trim().length() > 0)
                            ? LocalDate.parse(campos[6].trim())
                            : null;

                    Despesa despesa = new Despesa(descricao, valor, dataVencimento, categoria);
                    despesa.setPaga(paga);
                    despesa.setValorPendente(valorPendente);

                    // Se a despesa está paga e há uma data de pagamento, registramos o pagamento
                    if (paga && dataPag != null) {
                        despesa.registrarPagamento(valorPendente, dataPag);
                    }

                    despesas.add(despesa); // Adiciona a despesa à lista
                } else {
                    System.out.println("Formato de linha inválido: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar despesas: " + e.getMessage());
        }

    }

    public List<Despesa> getDespesasPorStatus(boolean pagas) {
        List<Despesa> despesasFiltradas = new ArrayList<>();
        for (Despesa despesa : despesas) {
            if (despesa.isPaga() == pagas) {
                despesasFiltradas.add(despesa);
            }
        }
        return despesasFiltradas;
    }

    // Carregar categorias do arquivo
    private void carregarCategoriasDoArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("categoria_despesas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length == 2) { // Verifica se a linha contém ID e nome
                    int id = Integer.parseInt(campos[0]);
                    String nome = campos[1];
                    categorias.put(id, new Categoria(id, nome)); // Adiciona ao mapa de categorias
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar categorias: " + e.getMessage());
        }
    }

    // Salvar categorias no arquivo
    private void salvarCategoriasEmArquivo() {
        try (FileWriter writer = new FileWriter("categoria_despesas.txt")) {
            for (Categoria categoria : categorias.values()) {
                writer.write(categoria.getId() + "," + categoria.getNome() + "\n"); // Formato: ID,nome
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }

    // Adicionar nova categoria
    public void adicionarCategoria(String nome) {
        Categoria novaCategoria = new Categoria(proximoIdCategoria++, nome); // Cria nova categoria com ID automático
        categorias.put(novaCategoria.getId(), novaCategoria); // Adiciona ao mapa de categorias
        salvarCategoriasEmArquivo(); // Salva alterações no arquivo
    }

    // Editar categoria existente
    public void editarCategoria(int id, String novoNome) {
        Categoria categoria = categorias.get(id);
        if (categoria != null) {
            categoria.setNome(novoNome); // Atualiza o nome da categoria
            salvarCategoriasEmArquivo(); // Salva alterações no arquivo
        } else {
            System.out.println("Categoria com ID " + id + " não encontrada.");
        }
    }

    // Listar todas as categorias
    public Map<Integer, Categoria> getCategorias() {
        return categorias; // Retorna o mapa de categorias
    }

    // Excluir categoria
    public void excluirCategoria(int id) {
        if (categorias.remove(id) != null) { // Remove a categoria do mapa
            salvarCategoriasEmArquivo(); // Salva alterações no arquivo
        } else {
            System.out.println("Categoria com ID " + id + " não encontrada.");
        }
    }
}