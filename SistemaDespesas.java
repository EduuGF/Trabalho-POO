// Arquivo 1: SistemaDespesas.java
import java.util.Scanner;

public class SistemaDespesas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorUsuarios gerenciadorUsuarios = new GerenciadorUsuarios(); // Instanciando Gerenciador de Usuários
        boolean autenticado = false;

        while (!autenticado) {
            System.out.println("=== Sistema de Despesas ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Login");
            System.out.println("3. Listar Usuários");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    // Opção para cadastrar um novo usuário
                    System.out.print("Digite o nome do usuário: ");
                    String nomeCadastro = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaCadastro = scanner.nextLine();
                    gerenciadorUsuarios.cadastrarUsuario(nomeCadastro, senhaCadastro);
                    break;
                case 2:
                    // Opção para login
                    System.out.print("Digite o nome do usuário: ");
                    String nomeLogin = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaLogin = scanner.nextLine();
                    autenticado = gerenciadorUsuarios.autenticarUsuario(nomeLogin, senhaLogin);
                    break;
                case 3:
                gerenciadorUsuarios.listarUsuarios();
                    break;
                case 4:
                    // Sair do sistema
                    System.out.println("Saindo do sistema. Até mais!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        // Após autenticação bem-sucedida, exibir o menu principal
        Menu menu = new Menu(); // Instanciando a classe Menu
        menu.exibirMenu(); // Chamando o método do menu
    }
}
