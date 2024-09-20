// Arquivo 7: Usuario.java
public class Usuario {
    private String nome;
    private String senhaCriptografada;

    // Construtor
    public Usuario(String nome, String senhaCriptografada) {
        this.nome = nome;
        this.senhaCriptografada = senhaCriptografada;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    @Override
    public String toString() {
        return nome + "," + senhaCriptografada;
    }
}
