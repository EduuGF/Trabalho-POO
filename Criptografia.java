// Arquivo 6: CryptoUtils.java
public class Criptografia {
    private static final int DESLOCAMENTO = 3; // Deslocamento da Cifra de César

    // Método para criptografar uma string
    public static String criptografar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char letra : texto.toCharArray()) {
            char letraCriptografada = (char) (letra + DESLOCAMENTO);
            resultado.append(letraCriptografada);
        }
        return resultado.toString();
    }

    // Método para descriptografar uma string
    public static String descriptografar(String textoCriptografado) {
        StringBuilder resultado = new StringBuilder();
        for (char letra : textoCriptografado.toCharArray()) {
            char letraDescriptografada = (char) (letra - DESLOCAMENTO);
            resultado.append(letraDescriptografada);
        }
        return resultado.toString();
    }
}
