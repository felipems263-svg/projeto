# Sistema Bancário em Java
### Projeto Final — Programação Orientada a Objetos

---

## 📋 Identificação do Projeto

| Campo         | Informação                              |
|---------------|-----------------------------------------|
| Disciplina    | Programação Orientada a Objetos         |
| Linguagem     | Java (JDK 17+)                          |
| Versão        | 1.0                                     |
| Ano           | 2025                                    |

---

## 🎯 Objetivo

Desenvolver um sistema bancário funcional via terminal, aplicando os
fundamentos de **Orientação a Objetos** em Java: encapsulamento,
herança, polimorfismo e abstração.

---

## 🏗️ Estrutura do Projeto

```
SistemaBancario/
└── src/
    ├── Main.java               → Ponto de entrada e interface com usuário
    ├── model/
    │   ├── Conta.java          → Classe abstrata base
    │   ├── ContaCorrente.java  → Herda Conta; suporte a cheque especial
    │   └── ContaPoupanca.java  → Herda Conta; suporte a rendimento mensal
    ├── service/
    │   └── BancoService.java   → Regras de negócio e repositório em memória
    └── util/
        └── Validador.java      → Validações centralizadas (utilitária)
```

---

## 🧩 Conceitos de POO Aplicados

| Conceito         | Onde foi aplicado                                              |
|------------------|----------------------------------------------------------------|
| **Abstração**    | Classe `Conta` abstrata — define contrato comum               |
| **Encapsulamento** | Atributos `private` com getters/setters em todas as classes |
| **Herança**      | `ContaCorrente` e `ContaPoupanca` estendem `Conta`            |
| **Polimorfismo** | Método `sacar()` sobrescrito em `ContaCorrente`               |
| **Separação de responsabilidades** | `Main` (UI) / `BancoService` (negócio) / `Validador` (validação) |

---

## ⚙️ Funcionalidades

- [x] Criar Conta Corrente
- [x] Criar Conta Poupança
- [x] Depositar
- [x] Sacar (com validação de senha e saldo)
- [x] Transferir entre contas
- [x] Consultar extrato
- [x] Listar todas as contas
- [x] Calcular patrimônio total

---

## 🛡️ Validações Implementadas

- Valores negativos ou zerados são rejeitados
- Saque bloqueado por saldo insuficiente
- Senha incorreta bloqueia saque e transferência
- Nome inválido (números ou muito curto) é rejeitado
- Senha curta (menos de 4 caracteres) é rejeitada
- Transferência para a própria conta é bloqueada
- Entradas não numéricas são tratadas sem encerrar o sistema

---

## ▶️ Como Executar

### Pré-requisito
- JDK 17 ou superior instalado
- IntelliJ IDEA Community (recomendado)

### Passos no IntelliJ IDEA
1. Abra o IntelliJ → **New Project** → Java
2. Copie as pastas `model/`, `service/`, `util/` para dentro de `src/`
3. Copie `Main.java` para a raiz de `src/`
4. Clique com o botão direito em `Main.java` → **Run 'Main.main()'**

---

## 🔮 Melhorias Futuras

- Interface gráfica com JavaFX ou Swing
- Persistência em banco de dados (JDBC / JPA)
- Histórico de transações por conta
- Autenticação com criptografia de senha (BCrypt)
- API REST para integração com aplicativo mobile

---

## 📝 Conclusão

O projeto permitiu aplicar na prática os pilares da Orientação a Objetos,
desenvolvendo um sistema coeso, validado e organizado em camadas. Além dos
conceitos técnicos, o trabalho reforçou habilidades de planejamento,
divisão de tarefas e resolução colaborativa de problemas.
