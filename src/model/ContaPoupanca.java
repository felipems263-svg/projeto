package model;

/**
 * Representa uma Conta Poupança no sistema bancário.
 *
 * <p>A conta poupança aplica rendimento mensal sobre o saldo
 * com base em uma taxa de juros configurável.</p>
 *
 * @author  Grupo — Projeto Final de Programação Orientada a Objetos
 * @version 1.0
 * @since   2025
 */
public class ContaPoupanca extends Conta {

    /** Taxa de rendimento mensal (ex: 0.005 = 0,5% ao mês). */
    private double taxaRendimento;

    // ---------------------------------------------------------------
    // Construtores
    // ---------------------------------------------------------------

    /**
     * Cria uma Conta Poupança com taxa de rendimento padrão de 0,5% ao mês.
     *
     * @param numeroConta número único da conta
     * @param titular     nome completo do titular
     * @param senha       senha de acesso
     */
    public ContaPoupanca(int numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "Conta Poupança");
        this.taxaRendimento = 0.005;
    }

    /**
     * Cria uma Conta Poupança com taxa de rendimento personalizada.
     *
     * @param numeroConta    número único da conta
     * @param titular        nome completo do titular
     * @param senha          senha de acesso
     * @param taxaRendimento taxa de rendimento mensal (ex: 0.005)
     */
    public ContaPoupanca(int numeroConta, String titular, String senha, double taxaRendimento) {
        super(numeroConta, titular, senha, "Conta Poupança");
        this.taxaRendimento = (taxaRendimento >= 0) ? taxaRendimento : 0.005;
    }

    // ---------------------------------------------------------------
    // Comportamento específico
    // ---------------------------------------------------------------

    /**
     * Aplica o rendimento mensal sobre o saldo atual.
     * Deve ser chamado mensalmente pela lógica de negócio.
     */
    public void aplicarRendimento() {
        double rendimento = getSaldo() * taxaRendimento;
        depositar(rendimento);
        System.out.printf("[OK] Rendimento de R$ %.2f (%.2f%%) aplicado na poupança.%n",
                rendimento, taxaRendimento * 100);
    }

    // ---------------------------------------------------------------
    // Getters e Setters
    // ---------------------------------------------------------------

    /** @return taxa de rendimento mensal */
    public double getTaxaRendimento() { return taxaRendimento; }

    /**
     * Atualiza a taxa de rendimento mensal.
     * @param taxa nova taxa (deve ser não-negativa)
     */
    public void setTaxaRendimento(double taxa) {
        if (taxa >= 0) this.taxaRendimento = taxa;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%s | Taxa: %.2f%%/mês", super.toString(), taxaRendimento * 100);
    }
}
