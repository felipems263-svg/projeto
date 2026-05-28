package service;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelo gerenciamento das contas bancárias.
 *
 * <p>Esta classe centraliza todas as operações de negócio do sistema,
 * como cadastro, busca e manutenção das contas. Aplica o padrão de
 * separação de responsabilidades (Separation of Concerns), mantendo
 * a lógica de negócio isolada da interface com o usuário.</p>
 *
 * @author  Grupo — Projeto Final de Programação Orientada a Objetos
 * @version 1.0
 * @since   2025
 */
public class BancoService {

    /** Nome do banco exibido no sistema. */
    public static final String NOME_BANCO = "Banco Java Digital";

    /** Repositório em memória das contas cadastradas. */
    private final List<Conta> contas;

    /** Contador automático de números de conta. */
    private int proximoNumeroConta;

    // ---------------------------------------------------------------
    // Construtor
    // ---------------------------------------------------------------

    /**
     * Inicializa o serviço bancário com lista vazia e numeração
     * de contas a partir de 1001.
     */
    public BancoService() {
        this.contas             = new ArrayList<>();
        this.proximoNumeroConta = 1001;
    }

    // ---------------------------------------------------------------
    // Criação de contas
    // ---------------------------------------------------------------

    /**
     * Cria e registra uma nova Conta Corrente.
     *
     * @param titular nome completo do titular
     * @param senha   senha de acesso
     * @return conta criada
     */
    public ContaCorrente criarContaCorrente(String titular, String senha) {
        ContaCorrente conta = new ContaCorrente(proximoNumeroConta++, titular, senha);
        contas.add(conta);
        System.out.printf("[OK] Conta Corrente %d criada para %s.%n",
                conta.getNumeroConta(), titular);
        return conta;
    }

    /**
     * Cria e registra uma nova Conta Poupança.
     *
     * @param titular nome completo do titular
     * @param senha   senha de acesso
     * @return conta criada
     */
    public ContaPoupanca criarContaPoupanca(String titular, String senha) {
        ContaPoupanca conta = new ContaPoupanca(proximoNumeroConta++, titular, senha);
        contas.add(conta);
        System.out.printf("[OK] Conta Poupança %d criada para %s.%n",
                conta.getNumeroConta(), titular);
        return conta;
    }

    // ---------------------------------------------------------------
    // Busca
    // ---------------------------------------------------------------

    /**
     * Busca uma conta pelo número.
     *
     * @param numeroConta número da conta desejada
     * @return {@link Optional} contendo a conta, ou vazio se não encontrada
     */
    public Optional<Conta> buscarPorNumero(int numeroConta) {
        return contas.stream()
                .filter(c -> c.getNumeroConta() == numeroConta)
                .findFirst();
    }

    /**
     * Busca contas pelo nome (parcial, sem distinção de maiúsculas/minúsculas).
     *
     * @param nome trecho do nome do titular
     * @return lista de contas que correspondem ao critério
     */
    public List<Conta> buscarPorNome(String nome) {
        List<Conta> resultado = new ArrayList<>();
        for (Conta c : contas) {
            if (c.getTitular().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ---------------------------------------------------------------
    // Consultas
    // ---------------------------------------------------------------

    /**
     * Retorna uma cópia da lista de todas as contas cadastradas.
     *
     * @return lista de contas
     */
    public List<Conta> listarContas() {
        return new ArrayList<>(contas);
    }

    /**
     * Retorna o total de contas cadastradas no sistema.
     *
     * @return número de contas
     */
    public int totalContas() {
        return contas.size();
    }

    /**
     * Calcula o saldo total de todas as contas (patrimônio gerido).
     *
     * @return soma dos saldos de todas as contas
     */
    public double calcularPatrimonioTotal() {
        return contas.stream().mapToDouble(Conta::getSaldo).sum();
    }
}
