package model;

/**
 * Representa uma Conta Corrente no sistema bancário.
 *
 * <p>A conta corrente possui um limite de cheque especial que permite
 * ao titular realizar saques além do saldo disponível, até o valor
 * do limite configurado.</p>
 *
 * @author  Grupo — Projeto Final de Programação Orientada a Objetos
 * @version 1.0
 * @since   2025
 */
public class ContaCorrente extends Conta {

    /** Limite disponível do cheque especial. */
    private double limite;

    // ---------------------------------------------------------------
    // Construtores
    // ---------------------------------------------------------------

    /**
     * Cria uma Conta Corrente sem cheque especial.
     *
     * @param numeroConta número único da conta
     * @param titular     nome completo do titular
     * @param senha       senha de acesso
     */
    public ContaCorrente(int numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "Conta Corrente");
        this.limite = 0.0;
    }

    /**
     * Cria uma Conta Corrente com cheque especial.
     *
     * @param numeroConta número único da conta
     * @param titular     nome completo do titular
     * @param senha       senha de acesso
     * @param limite      valor do limite do cheque especial
     */
    public ContaCorrente(int numeroConta, String titular, String senha, double limite) {
        super(numeroConta, titular, senha, "Conta Corrente");
        this.limite = (limite >= 0) ? limite : 0.0;
    }

    // ---------------------------------------------------------------
    // Sobrescrita — permite saque até o limite do cheque especial
    // ---------------------------------------------------------------

    /**
     * Realiza saque considerando o saldo disponível mais o limite do
     * cheque especial. Usa {@code setSaldo()} da classe pai para
     * atualizar o saldo diretamente, sem chamadas encadeadas que
     * gerariam mensagens duplicadas.
     *
     * @param valor          valor a ser sacado
     * @param senhaInformada senha fornecida pelo usuário
     * @return {@code true} se o saque foi efetuado; {@code false} caso contrário
     */
    @Override
    public boolean sacar(double valor, String senhaInformada) {
        if (!autenticar(senhaInformada)) {
            System.out.println("[ERRO] Senha incorreta. Operação negada.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("[ERRO] O valor do saque deve ser positivo.");
            return false;
        }
        double saldoDisponivel = getSaldo() + limite;
        if (valor > saldoDisponivel) {
            System.out.printf("[ERRO] Saldo + limite insuficientes. Disponível: R$ %.2f%n",
                    saldoDisponivel);
            return false;
        }
        // Atualiza saldo diretamente via método protegido da superclasse
        setSaldo(getSaldo() - valor);
        System.out.printf("[OK] Saque de R$ %.2f realizado com sucesso.%n", valor);
        return true;
    }

    // ---------------------------------------------------------------
    // Getters e Setters
    // ---------------------------------------------------------------

    /** @return limite do cheque especial */
    public double getLimite() { return limite; }

    /**
     * Atualiza o limite do cheque especial.
     * @param limite novo limite (deve ser não-negativo)
     */
    public void setLimite(double limite) {
        if (limite >= 0) this.limite = limite;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%s | Limite: R$ %.2f", super.toString(), limite);
    }
}
