import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorUsuarios {
    private Map<String, Usuario> usuarios; // Mapa de usuários, chave: nome

    public GerenciadorUsuarios() {
        this.usuarios = new HashMap<>();
        carregarUsuariosDoArquivo();
    }

    // Carregar usuários do arquivo
    private void carregarUsuariosDoArquivo() {
        File arquivo = new File("usuarios.txt");
        if (!arquivo.exists()) {
            return; // Nenhum usuário cadastrado ainda
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length == 2) {
                    String nome = campos[0];
                    String senhaCriptografada = campos[1];
                    usuarios.put(nome, new Usuario(nome, senhaCriptografada));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // Salvar usuário no arquivo
    private void salvarUsuarioEmArquivo(Usuario usuario) {
        try (FileWriter writer = new FileWriter("usuarios.txt", true)) {
            writer.write(usuario.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    // Método para cadastrar um novo usuário
    public boolean cadastrarUsuario(String nome, String senha) {
        if (usuarios.containsKey(nome)) {
            System.out.println("Usuário já existe.");
            return false;
        }
        try {
            String senhaCriptografada = Criptografia.criptografar(senha); // Criptografar a senha
            Usuario novoUsuario = new Usuario(nome, senhaCriptografada);
            usuarios.put(nome, novoUsuario);
            salvarUsuarioEmArquivo(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao criptografar a senha: " + e.getMessage());
            return false;
        }
    }

    // Método para autenticar um usuário
    public boolean autenticarUsuario(String nome, String senha) {
        if (!usuarios.containsKey(nome)) {
            System.out.println("Usuário não encontrado.");
            return false;
        }
        Usuario usuario = usuarios.get(nome);
        try {
            String senhaDescriptografada = Criptografia.descriptografar(usuario.getSenhaCriptografada());
            if (senhaDescriptografada.equals(senha)) {
                System.out.println("Autenticação bem-sucedida.");
                return true;
            } else {
                System.out.println("Senha incorreta.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao descriptografar a senha: " + e.getMessage());
            return false;
        }
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("Usuários cadastrados:");
            for (Usuario usuario : usuarios.values()) {
                System.out.println(usuario.getNome());
            }
        }
    }
}
