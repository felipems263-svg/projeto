package model;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 *
 * <p>Esta classe serve como base para os diferentes tipos de conta
 * disponíveis no sistema. Implementa os atributos e comportamentos
 * comuns a qualquer conta bancária.</p>
 *
 * @author  Grupo — Projeto Final de Programação Orientada a Objetos
 * @version 1.0
 * @since   2025
 */
public abstract class Conta {

    // ---------------------------------------------------------------
    // Atributos
    // ---------------------------------------------------------------

    /** Número único de identificação da conta. */
    private final int numeroConta;

    /** Nome completo do titular da conta. */
    private String titular;

    /** Saldo atual disponível na conta. */
    private double saldo;

    /** Senha de acesso à conta (uso interno). */
    private String senha;

    /** Tipo da conta (ex: "Corrente", "Poupança"). */
    private final String tipoConta;

    // ---------------------------------------------------------------
    // Construtor
    // ---------------------------------------------------------------

    /**
     * Constrói uma nova conta com os dados informados.
     *
     * @param numeroConta número único da conta
     * @param titular     nome completo do titular
     * @param senha       senha de acesso
     * @param tipoConta   tipo da conta bancária
     */
    public Conta(int numeroConta, String titular, String senha, String tipoConta) {
        this.numeroConta = numeroConta;
        this.titular     = titular;
        this.senha       = senha;
        this.tipoConta   = tipoConta;
        this.saldo       = 0.0;
    }

    // ---------------------------------------------------------------
    // Métodos concretos
    // ---------------------------------------------------------------

    /**
     * Realiza um depósito na conta.
     *
     * @param valor valor a ser depositado (deve ser positivo)
     * @return {@code true} se o depósito foi efetuado com sucesso;
     *         {@code false} caso contrário
     */
    public boolean depositar(double valor) {
        if (valor <= 0) {
            System.out.println("[ERRO] O valor do depósito deve ser positivo.");
            return false;
        }
        this.saldo += valor;
        System.out.printf("[OK] Depósito de R$ %.2f realizado com sucesso.%n", valor);
        return true;
    }

    /**
     * Realiza um saque na conta, mediante validação de senha e saldo.
     *
     * @param valor           valor a ser sacado
     * @param senhaInformada  senha fornecida pelo usuário
     * @return {@code true} se o saque foi efetuado com sucesso;
     *         {@code false} caso contrário
     */
    public boolean sacar(double valor, String senhaInformada) {
        if (!autenticar(senhaInformada)) {
            System.out.println("[ERRO] Senha incorreta. Operação negada.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("[ERRO] O valor do saque deve ser positivo.");
            return false;
        }
        if (valor > this.saldo) {
            System.out.println("[ERRO] Saldo insuficiente para realizar o saque.");
            return false;
        }
        this.saldo -= valor;
        System.out.printf("[OK] Saque de R$ %.2f realizado com sucesso.%n", valor);
        return true;
    }

    /**
     * Realiza uma transferência para outra conta.
     *
     * @param destino         conta de destino da transferência
     * @param valor           valor a ser transferido
     * @param senhaInformada  senha de confirmação
     * @return {@code true} se a transferência foi realizada com sucesso;
     *         {@code false} caso contrário
     */
    public boolean transferir(Conta destino, double valor, String senhaInformada) {
        if (destino == null) {
            System.out.println("[ERRO] Conta de destino inválida.");
            return false;
        }
        if (this.numeroConta == destino.getNumeroConta()) {
            System.out.println("[ERRO] Não é possível transferir para a própria conta.");
            return false;
        }
        // Validações antes de mover qualquer valor
        if (!autenticar(senhaInformada)) {
            System.out.println("[ERRO] Senha incorreta. Operação negada.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("[ERRO] O valor da transferência deve ser positivo.");
            return false;
        }
        if (valor > this.saldo) {
            System.out.println("[ERRO] Saldo insuficiente para realizar a transferência.");
            return false;
        }
        this.saldo      -= valor;
        destino.saldo   += valor;
        System.out.printf("[OK] Transferência de R$ %.2f para %s (conta %d) realizada.%n",
                valor, destino.getTitular(), destino.getNumeroConta());
        return true;
    }

    /**
     * Exibe o extrato resumido da conta no console.
     */
    public void exibirExtrato() {
        System.out.println("=".repeat(45));
        System.out.printf("  Tipo     : %s%n",   tipoConta);
        System.out.printf("  Conta N. : %d%n",   numeroConta);
        System.out.printf("  Titular  : %s%n",   titular);
        System.out.printf("  Saldo    : R$ %.2f%n", saldo);
        System.out.println("=".repeat(45));
    }

    /**
     * Valida a senha informada com a senha armazenada na conta.
     *
     * @param senhaInformada senha fornecida para verificação
     * @return {@code true} se as senhas coincidem; {@code false} caso contrário
     */
    public boolean autenticar(String senhaInformada) {
        return this.senha != null && this.senha.equals(senhaInformada);
    }

    // ---------------------------------------------------------------
    // Getters, Setters e método protegido para subclasses
    // ---------------------------------------------------------------

    /** @return número da conta */
    public int getNumeroConta()   { return numeroConta; }

    /** @return nome do titular */
    public String getTitular()    { return titular; }

    /** @return saldo atual */
    public double getSaldo()      { return saldo; }

    /** @return tipo da conta */
    public String getTipoConta()  { return tipoConta; }

    /**
     * Atualiza o nome do titular.
     * @param titular novo nome
     */
    public void setTitular(String titular) { this.titular = titular; }

    /**
     * Atualiza a senha de acesso.
     * @param senha nova senha
     */
    public void setSenha(String senha) { this.senha = senha; }

    /**
     * Permite que subclasses ajustem o saldo diretamente (uso interno).
     * Visibilidade protegida — não exposta ao código externo.
     *
     * @param novoSaldo novo valor do saldo
     */
    protected void setSaldo(double novoSaldo) { this.saldo = novoSaldo; }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Retorna uma representação textual resumida da conta.
     *
     * @return string com tipo, número e titular da conta
     */
    @Override
    public String toString() {
        return String.format("[%s] Conta %d - Titular: %s", tipoConta, numeroConta, titular);
    }
}
