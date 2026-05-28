import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import service.BancoService;
import util.Validador;
import java.io.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
public class Main {

    // ---------------------------------------------------------------
    // Constantes do menu
    // ---------------------------------------------------------------
    private static final int MENU_CRIAR_CORRENTE = 1;
    private static final int MENU_CRIAR_POUPANCA = 2;
    private static final int MENU_DEPOSITAR      = 3;
    private static final int MENU_SACAR          = 4;
    private static final int MENU_TRANSFERIR     = 5;
    private static final int MENU_EXTRATO        = 6;
    private static final int MENU_LISTAR         = 7;
    private static final int MENU_SAIR           = 0;

    // ---------------------------------------------------------------
    // Atributos estáticos (escopo da aplicação)
    // ---------------------------------------------------------------
    private static final Scanner scanner =
            new Scanner(System.in, StandardCharsets.UTF_8);

    private static final BancoService banco =
            new BancoService();

    private static final String ARQUIVO_CONTAS = "contas.txt";


    // ---------------------------------------------------------------
    // Ponto de entrada
    // ---------------------------------------------------------------

    private static void salvarConta(Conta conta) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(ARQUIVO_CONTAS, true))) {

            writer.write(conta.toString());
            writer.newLine();

        } catch (IOException e) {

            System.out.println("[ERRO] Falha ao salvar conta.");

        }
    }

    private static void carregarContas() {

        File arquivo = new File(ARQUIVO_CONTAS);

        if (!arquivo.exists()) {
            return;
        }

        System.out.println("\n--- CONTAS SALVAS ---");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(arquivo))) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                System.out.println(">> " + linha);

            }

            System.out.println();

        } catch (IOException e) {

            System.out.println("[ERRO] Falha ao carregar contas.");

        }
    }
    public static void main(String[] args) {
        // Garante que acentos e caracteres especiais apareçam corretamente
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        exibirBanner();
        carregarContas();
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Opcao");

            switch (opcao) {
                case MENU_CRIAR_CORRENTE -> fluxoCriarContaCorrente();
                case MENU_CRIAR_POUPANCA -> fluxoCriarContaPoupanca();
                case MENU_DEPOSITAR      -> fluxoDepositar();
                case MENU_SACAR          -> fluxoSacar();
                case MENU_TRANSFERIR     -> fluxoTransferir();
                case MENU_EXTRATO        -> fluxoExtrato();
                case MENU_LISTAR         -> fluxoListarContas();
                case MENU_SAIR           -> System.out.println(
                        "\nSistema encerrado. Obrigado por utilizar o "
                        + BancoService.NOME_BANCO + ".\n");
                default                  -> System.out.println(
                        "[AVISO] Opcao invalida. Tente novamente.\n");
            }

        } while (opcao != MENU_SAIR);

        scanner.close();
    }

    // ---------------------------------------------------------------
    // Fluxos de operações (casos de uso)
    // ---------------------------------------------------------------


    private static void fluxoCriarContaCorrente() {
        System.out.println("\n--- NOVA CONTA CORRENTE ---");
        String titular = lerTexto("Nome do titular");
        if (!Validador.nomeValido(titular)) {
            System.out.println("[ERRO] Nome invalido. Use apenas letras (minimo 3 caracteres).");
            return;
        }
        String senha = lerTexto("Senha (minimo 4 caracteres)");
        if (!Validador.senhaValida(senha)) {

            System.out.printf("[ERRO] Senha deve ter no minimo %d caracteres.%n",
                    Validador.SENHA_TAMANHO_MINIMO);
            return;
        }
        ContaCorrente conta = banco.criarContaCorrente(titular, senha);
        salvarConta(conta);
        System.out.println("Conta criada: " + conta + "\n");

    }


    private static void fluxoCriarContaPoupanca() {
        System.out.println("\n--- NOVA CONTA POUPANCA ---");
        String titular = lerTexto("Nome do titular");
        if (!Validador.nomeValido(titular)) {
            System.out.println("[ERRO] Nome invalido. Use apenas letras (minimo 3 caracteres).");
            return;
        }
        String senha = lerTexto("Senha (minimo 4 caracteres)");
        if (!Validador.senhaValida(senha)) {
            System.out.printf("[ERRO] Senha deve ter no minimo %d caracteres.%n",
                    Validador.SENHA_TAMANHO_MINIMO);
            return;
        }
        ContaPoupanca conta = banco.criarContaPoupanca(titular, senha);
        salvarConta(conta);
        System.out.println("Conta criada: " + conta + "\n");
    }


    private static void fluxoDepositar() {
        System.out.println("\n--- DEPOSITO ---");
        Conta conta = buscarContaOuAvisar();
        if (conta == null) return;

        double valor = lerDouble("Valor do deposito (R$)");
        if (!Validador.valorPositivo(valor)) {
            System.out.println("[ERRO] O valor informado deve ser positivo.");
            return;
        }
        conta.depositar(valor);
        System.out.println();
    }


    private static void fluxoSacar() {
        System.out.println("\n--- SAQUE ---");
        Conta conta = buscarContaOuAvisar();
        if (conta == null) return;

        String senha = lerTexto("Senha");
        double valor = lerDouble("Valor do saque (R$)");
        if (!Validador.valorPositivo(valor)) {
            System.out.println("[ERRO] O valor informado deve ser positivo.");
            return;
        }
        conta.sacar(valor, senha);
        System.out.println();
    }


    private static void fluxoTransferir() {
        System.out.println("\n--- TRANSFERENCIA ---");

        System.out.print("Conta de ORIGEM  - ");
        Conta origem = buscarContaOuAvisar();
        if (origem == null) return;

        System.out.print("Conta de DESTINO - ");
        Conta destino = buscarContaOuAvisar();
        if (destino == null) return;

        String senha = lerTexto("Senha da conta de origem");
        double valor = lerDouble("Valor a transferir (R$)");
        if (!Validador.valorPositivo(valor)) {
            System.out.println("[ERRO] O valor informado deve ser positivo.");
            return;
        }
        origem.transferir(destino, valor, senha);
        System.out.println();
    }


    private static void fluxoExtrato() {
        System.out.println("\n--- EXTRATO ---");
        Conta conta = buscarContaOuAvisar();
        if (conta == null) return;
        conta.exibirExtrato();
        System.out.println();
    }


    private static void fluxoListarContas() {

        System.out.println("\n--- CONTAS CADASTRADAS ---");

        File arquivo = new File(ARQUIVO_CONTAS);

        if (!arquivo.exists()) {

            System.out.println("Nenhuma conta cadastrada.\n");
            return;

        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(arquivo))) {

            String linha;
            int total = 0;

            while ((linha = reader.readLine()) != null) {

                System.out.println(">> " + linha);
                total++;

            }

            System.out.printf("%nTotal de contas: %d%n%n", total);

        } catch (IOException e) {

            System.out.println("[ERRO] Falha ao listar contas.");

        }
    }

    // ---------------------------------------------------------------
    // Auxiliares de interface
    // ---------------------------------------------------------------


    private static Conta buscarContaOuAvisar() {
        int numero = lerInteiro("Numero da conta");
        if (numero == -1) return null;
        Optional<Conta> resultado = banco.buscarPorNumero(numero);
        if (resultado.isEmpty()) {
            System.out.println("[ERRO] Conta " + numero + " nao encontrada.\n");
            return null;
        }
        return resultado.get();
    }


    private static String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return scanner.nextLine().trim();
    }


    private static int lerInteiro(String rotulo) {
        System.out.print(rotulo + ": ");
        String entrada = scanner.nextLine().trim();
        if (!Validador.ehInteiro(entrada)) {
            System.out.println("[ERRO] Valor numerico inteiro esperado.\n");
            return -1;
        }
        return Integer.parseInt(entrada);
    }


    private static double lerDouble(String rotulo) {
        System.out.print(rotulo + ": ");
        String entrada = scanner.nextLine().trim().replace(",", ".");
        if (!Validador.ehDouble(entrada)) {
            System.out.println("[ERRO] Valor numerico decimal esperado.\n");
            return -1.0;
        }
        return Double.parseDouble(entrada);
    }

    // ---------------------------------------------------------------
    // Exibição de tela
    // ---------------------------------------------------------------


    private static void exibirBanner() {
        System.out.println("==================================================");
        System.out.println("   " + BancoService.NOME_BANCO.toUpperCase());
        System.out.println("   Sistema de Gerenciamento de Contas - v1.0");
        System.out.println("==================================================\n");
    }


    private static void exibirMenu() {
        System.out.println("---------- MENU PRINCIPAL ----------");
        System.out.println("  1 - Criar Conta Corrente");
        System.out.println("  2 - Criar Conta Poupanca");
        System.out.println("  3 - Depositar");
        System.out.println("  4 - Sacar");
        System.out.println("  5 - Transferir");
        System.out.println("  6 - Consultar Extrato");
        System.out.println("  7 - Listar Todas as Contas");
        System.out.println("  0 - Sair");
        System.out.println("------------------------------------");
    }
}
