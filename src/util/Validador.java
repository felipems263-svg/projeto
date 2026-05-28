package util;

/**
 * Classe utilitária de validação de dados de entrada.
 *
 * <p>Centraliza as regras de validação do sistema, garantindo
 * consistência e reutilização em toda a aplicação.
 * Todos os métodos são estáticos, pois não dependem de estado.</p>
 *
 * @author  Grupo — Projeto Final de Programação Orientada a Objetos
 * @version 1.0
 * @since   2025
 */
public final class Validador {

    /** Tamanho mínimo aceito para uma senha. */
    public static final int SENHA_TAMANHO_MINIMO = 4;

    /**
     * Construtor privado — impede instanciação de classe utilitária.
     */
    private Validador() {}

    // ---------------------------------------------------------------
    // Validações de texto
    // ---------------------------------------------------------------

    /**
     * Verifica se um texto não é nulo nem vazio após remoção de espaços.
     *
     * @param texto string a ser verificada
     * @return {@code true} se o texto é válido (não vazio)
     */
    public static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Valida o nome do titular (mínimo de 3 caracteres, sem números).
     *
     * @param nome nome a ser validado
     * @return {@code true} se o nome é válido
     */
    public static boolean nomeValido(String nome) {
        return textoValido(nome)
                && nome.trim().length() >= 3
                && nome.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    // ---------------------------------------------------------------
    // Validações de senha
    // ---------------------------------------------------------------

    /**
     * Valida se a senha possui o tamanho mínimo exigido.
     *
     * @param senha senha a ser validada
     * @return {@code true} se a senha é válida
     */
    public static boolean senhaValida(String senha) {
        return textoValido(senha) && senha.length() >= SENHA_TAMANHO_MINIMO;
    }

    // ---------------------------------------------------------------
    // Validações numéricas
    // ---------------------------------------------------------------

    /**
     * Verifica se um valor monetário é positivo (maior que zero).
     *
     * @param valor valor a ser verificado
     * @return {@code true} se o valor é positivo
     */
    public static boolean valorPositivo(double valor) {
        return valor > 0;
    }

    /**
     * Verifica se uma string pode ser convertida para número inteiro.
     *
     * @param texto string a ser verificada
     * @return {@code true} se o texto representa um inteiro válido
     */
    public static boolean ehInteiro(String texto) {
        if (!textoValido(texto)) return false;
        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se uma string pode ser convertida para número decimal.
     *
     * @param texto string a ser verificada
     * @return {@code true} se o texto representa um double válido
     */
    public static boolean ehDouble(String texto) {
        if (!textoValido(texto)) return false;
        try {
            Double.parseDouble(texto.trim().replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
