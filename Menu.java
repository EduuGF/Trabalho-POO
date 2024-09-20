import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

//Criando classe menu e instanciando um Gerenciador de despesas
public class Menu {
    private GerenciadorDespesas gerenciadorDespesas = new GerenciadorDespesas();

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 5) {
            System.out.println("Menu Principal:");
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas");
            System.out.println("4. Gerenciar Tipos de Despesa");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    inserirDespesa();
                    break;
                case 2:
                    registrarPagamento();
                    break;
                case 3:
                    exibirSubmenuFiltroDespesas();
                    break;
                case 4:
                    gerenciarCategorias();
                    break;
            }
        }

        scanner.close();
    }

    private void inserirDespesa() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Digite a descrição da despesa: ");
        String descricao = scanner.nextLine();
    
        System.out.print("Digite o valor da despesa: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
    
        System.out.print("Digite a data de vencimento (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        LocalDate dataVencimento = LocalDate.parse(dataStr);
    
        // Alteração: Listar todas as categorias disponíveis com seus IDs
        System.out.println("Categorias disponíveis:");
        Collection<Categoria> categorias = gerenciadorDespesas.getCategorias().values();
        for (Categoria cat : categorias) {
            System.out.println(cat.getId() + ": " + cat.getNome());
        }
    
        // Alteração: Solicitar ao usuário que selecione a categoria pelo ID
        System.out.print("Digite o ID da categoria da despesa: ");
        int idCategoria = scanner.nextInt();
        scanner.nextLine();
    
        // Alteração: Obter o nome da categoria a partir do ID selecionado
        Categoria categoriaSelecionada = gerenciadorDespesas.getCategorias().get(idCategoria);
        if (categoriaSelecionada == null) {
            System.out.println("ID de categoria inválido. Despesa não cadastrada.");
            return; // Encerrar o método se o ID for inválido
        }
        String categoria = categoriaSelecionada.getNome();
    
        Despesa novaDespesa = new Despesa(descricao, valor, dataVencimento, categoria);
    
        gerenciadorDespesas.adicionarDespesa(novaDespesa);
    }

    private void registrarPagamento() {
        Scanner scanner = new Scanner(System.in);

        // Listar todas as despesas
        System.out.println("Despesas cadastradas:");
        List<Despesa> despesas = gerenciadorDespesas.getDespesas();
        for (int i = 0; i < despesas.size(); i++) {
            System.out.println(i + ": " + despesas.get(i));
        }

        // Permitir que o usuário selecione a despesa que deseja pagar
        System.out.print("Digite o número da despesa a ser paga: ");
        int indice = scanner.nextInt();

        // Solicitar o valor a ser pago
        System.out.print("Digite o valor a ser pago: ");
        double valorPago = scanner.nextDouble();
        scanner.nextLine();

        // Solicitar a data de pagamento
        System.out.print("Digite a data do pagamento (YYYY-MM-DD): ");
        String dataPagStr = scanner.nextLine();
        LocalDate dataPag = LocalDate.parse(dataPagStr);

        // Registrar o pagamento da despesa selecionada
        gerenciadorDespesas.registrarPagamento(indice, valorPago, dataPag);
    }

    private void exibirSubmenuFiltroDespesas() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar ao usuário a escolha de filtro
        System.out.println("Selecione o filtro:");
        System.out.println("1. Despesas Pagas");
        System.out.println("2. Despesas Não Pagas");
        System.out.print("Escolha uma opção: ");
        int filtroOpcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        // Validar a opção e listar as despesas correspondentes
        switch (filtroOpcao) {
            case 1:
                listarDespesas(true); // Filtrar despesas pagas
                break;
            case 2:
                listarDespesas(false); // Filtrar despesas não pagas
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void listarDespesas(boolean pagas) {
        List<Despesa> despesasFiltradas = gerenciadorDespesas.getDespesasPorStatus(pagas);

        if (despesasFiltradas.isEmpty()) {
            String tipoDespesa = pagas ? "pagas" : "não pagas";
            System.out.println("Nenhuma despesa " + tipoDespesa + " encontrada.");
        } else {
            String tipoDespesa = pagas ? "pagas" : "em aberto";
            System.out.println("Despesas " + tipoDespesa + ":");
            for (Despesa despesa : despesasFiltradas) {
                System.out.println(despesa);
            }
        }

    }

    private void gerenciarCategorias() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gerenciar Categorias:");
        System.out.println("1. Adicionar Nova Categoria");
        System.out.println("2. Editar Categoria Existente");
        System.out.println("3. Listar Todas as Categorias");
        System.out.println("4. Excluir Categoria");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        switch (opcao) {
            case 1:
                System.out.print("Digite o nome da nova categoria: ");
                String nome = scanner.nextLine();
                gerenciadorDespesas.adicionarCategoria(nome);
                System.out.println("Categoria adicionada com sucesso.");
                break;
            case 2:
                System.out.print("Digite o ID da categoria a ser editada: ");
                int idEdicao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha
                System.out.print("Digite o novo nome da categoria: ");
                String novoNome = scanner.nextLine();
                gerenciadorDespesas.editarCategoria(idEdicao, novoNome);
                System.out.println("Categoria editada com sucesso.");
                break;
            case 3:
                System.out.println("Categorias existentes:");
                for (Categoria categoria : gerenciadorDespesas.getCategorias().values()) {
                    System.out.println(categoria);
                }
                break;
            case 4:
                System.out.print("Digite o ID da categoria a ser excluída: ");
                int idExcluir = scanner.nextInt();
                gerenciadorDespesas.excluirCategoria(idExcluir);
                System.out.println("Categoria excluída com sucesso.");
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}