package service;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

/**
 * Serviço responsável pelo gerenciamento das contas bancárias.
 */
public class BancoService {

    /** Nome do banco exibido no sistema. */
    public static final String NOME_BANCO = "Banco Java Digital";

    /** Vetor de contas cadastradas. */
    private final Conta[] contas;

    /** Quantidade atual de contas cadastradas. */
    private int totalContas;

    /** Contador automático de números de conta. */
    private int proximoNumeroConta;

    // ---------------------------------------------------------------
    // Construtor
    // ---------------------------------------------------------------

    /**
     * Inicializa o serviço bancário.
     */
    public BancoService() {
        this.contas = new Conta[100];
        this.totalContas = 0;
        this.proximoNumeroConta = 1001;
    }

    // ---------------------------------------------------------------
    // Criação de contas
    // ---------------------------------------------------------------

    public ContaCorrente criarContaCorrente(String titular, String senha) {

        ContaCorrente conta =
                new ContaCorrente(proximoNumeroConta++, titular, senha);

        contas[totalContas] = conta;
        totalContas++;

        System.out.printf("[OK] Conta Corrente %d criada para %s.%n",
                conta.getNumeroConta(), titular);

        return conta;
    }

    public ContaPoupanca criarContaPoupanca(String titular, String senha) {

        ContaPoupanca conta =
                new ContaPoupanca(proximoNumeroConta++, titular, senha);

        contas[totalContas] = conta;
        totalContas++;

        System.out.printf("[OK] Conta Poupança %d criada para %s.%n",
                conta.getNumeroConta(), titular);

        return conta;
    }

    // ---------------------------------------------------------------
    // Busca
    // ---------------------------------------------------------------

    public Conta buscarPorNumero(int numeroConta) {

        for (int i = 0; i < totalContas; i++) {

            if (contas[i].getNumeroConta() == numeroConta) {
                return contas[i];
            }
        }

        return null;
    }

    public Conta[] buscarPorNome(String nome) {

        Conta[] resultado = new Conta[100];
        int encontrados = 0;

        for (int i = 0; i < totalContas; i++) {

            if (contas[i].getTitular()
                    .toLowerCase()
                    .contains(nome.toLowerCase())) {

                resultado[encontrados] = contas[i];
                encontrados++;
            }
        }

        return resultado;
    }

    // ---------------------------------------------------------------
    // Consultas
    // ---------------------------------------------------------------

    public Conta[] listarContas() {
        return contas;
    }

    public int totalContas() {
        return totalContas;
    }

    public double calcularPatrimonioTotal() {

        double total = 0;

        for (int i = 0; i < totalContas; i++) {
            total += contas[i].getSaldo();
        }

        return total;
    }
}
