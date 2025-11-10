# Padrões de Projeto - Atividade Prática

Este repositório contém as soluções para uma atividade prática sobre Padrões de Projeto (*Design Patterns*). O objetivo é aplicar os padrões mais adequados para resolver problemas específicos de arquitetura de software, com foco nos princípios **SOLID**.

Cada questão está contida em seu próprio pacote (`Questao1`, `Questao2`, etc.) e inclui uma classe `Main.java` para demonstrar a solução em funcionamento.

---

## 🚀 Questão 1: Sistema de Análise de Risco

### O Problema

O sistema precisava calcular métricas de risco usando diferentes algoritmos (ex: *Value at Risk*, *Expected Shortfall*) que deveriam ser **intercambiáveis em tempo de execução**.

### Padrão Escolhido: Strategy (Estratégia)

O padrão **Strategy** é um padrão comportamental que define uma família de algoritmos, encapsula cada um deles em classes separadas e os torna intercambiáveis.

### justificativa da Escolha

Esta foi a escolha ideal pelos seguintes motivos:

1.  **Intercambialidade (Requisito Chave):** O padrão permite que o cliente (o `RiskAnalysisProcessor`) altere o algoritmo dinamicamente através do método `setAlgorithmStrategy()`.
2.  **Princípio Aberto/Fechado (OCP):** O sistema está *aberto para extensão*, mas *fechado para modificação*. Podemos adicionar um novo algoritmo de risco (ex: `MonteCarloStrategy`) apenas criando uma nova classe que implementa a interface `IRiskAlgorithmStrategy`, **sem alterar uma linha** do `RiskAnalysisProcessor`.
3.  **Princípio da Responsabilidade Única (SRP):** Cada classe de estratégia (ex: `ValueAtRiskStrategy`) tem uma única responsabilidade: seu cálculo específico. A classe `RiskAnalysisProcessor` (o *Contexto*) tem apenas a responsabilidade de gerenciar o contexto e delegar a execução.
4.  **Inversão de Dependência (DIP):** A classe de alto nível `RiskAnalysisProcessor` não depende das implementações de baixo nível (os algoritmos concretos). Ambos dependem da abstração (`IRiskAlgorithmStrategy`).



---

## 🔌 Questão 2: Integração com Legado Bancário

### O Problema

Integrar um sistema moderno com uma interface clara (ex: `autorizar(cartao, valor, moeda)`) a um sistema bancário legado que possui uma interface incompatível (ex: `processarTransacao(HashMap)`) e regras de negócio obscuras (ex: `"BRL" = 3`).

### Padrão Escolhido: Adapter (Adaptador)

O padrão **Adapter** é um padrão estrutural que atua como um "tradutor" ou "ponte", permitindo que interfaces incompatíveis trabalhem juntas.

### justificativa da Escolha

1.  **Tradução de Interface (Propósito Principal):** O `TransacaoAdapter` implementa a interface moderna (`ProcessadorTransacoes`) que o cliente espera, mas internamente ele "envolve" (*wraps*) e chama o sistema legado (`SistemaBancarioLegado`).
2.  **Encapsulamento da Lógica de Tradução:** Toda a lógica "suja" de conversão de dados fica contida dentro do Adapter:
    * **Requisição:** Converte `(String, double, "BRL")` no `HashMap` esperado.
    * **Dados Faltantes:** Adiciona campos obrigatórios que o legado exige mas o cliente moderno não conhece (ex: `CODIGO_LOJA`).
    * **Bidirecional (Requisito Chave):** Traduz o `HashMap` de *resposta* do legado em um objeto de resposta limpo (`RespostaAutorizacao`) para o cliente.
3.  **Princípio da Responsabilidade Única (SRP):** O Adapter tem uma única responsabilidade: mediar e traduzir a comunicação. Isso mantém o cliente moderno e o serviço legado totalmente independentes e limpos de lógica de integração.



---

## 🚦 Questão 3: Máquina de Estados de Usina Nuclear

### O Problema

Modelar um objeto (`UsinaNuclear`) que muda seu comportamento radicalmente com base em seu estado interno (DESLIGADA, OPERACAO_NORMAL, EMERGENCIA, etc.). As regras de transição entre os estados são complexas e críticas para a segurança (ex: só pode ir para EMERGÊNCIA a partir de ALERTA_VERMELHO).

### Padrão Escolhido: State (Estado)

O padrão **State** é um padrão comportamental que permite a um objeto alterar seu comportamento quando seu estado interno muda. O objeto parecerá mudar de classe.

### justificativa da Escolha

1.  **Evita Condicionais Complexas:** A alternativa seria um método `monitorar()` gigante na classe `UsinaNuclear` com um `switch` ou `if/else` massivo para tratar cada estado. O padrão State move essa lógica para classes separadas.
2.  **Segurança e Transições Rígidas (Requisito Chave):** A lógica de transição é encapsulada *dentro* de cada estado.
    * `EstadoOperacaoNormal` só tem código para transicionar para `ALERTA_AMARELO`.
    * `EstadoAlertaVermelho` é a **única** classe com a lógica para transicionar para `EstadoEmergencia`.
    * Isso torna impossível uma transição perigosa e circular (ex: `OPERACAO_NORMAL` -> `EMERGENCIA`).
3.  **Princípio Aberto/Fechado (OCP):** O sistema é fácil de estender. Para adicionar um novo estado (ex: `EstadoEvacuacao`), basta criar uma nova classe que implementa `IUsinaState` e atualizar as transições nos estados adjacentes, sem modificar a classe `UsinaNuclear`.
4.  **Sobrescrita de Comportamento (Requisito "Manutenção"):** O `EstadoManutencao` é simplesmente outro estado que implementa a mesma interface, mas seus métodos (como `ligar()` ou `monitorar()`) são implementados para bloquear ações ou ignorar sensores, cumprindo o requisito de "sobreescrever" o comportamento normal.



---

## ⛓️ Questão 4: Cadeia de Validação de NF-e

### O Problema

Validar um documento fiscal (NF-e) passando-o por uma cadeia de validadores (XML, Certificado, Regras Fiscais, etc.). A cadeia possui requisitos complexos:
* **Circuit Breaker:** Parar a cadeia após 3 falhas.
* **Rollback:** Desfazer ações de validadores anteriores se um posterior falhar.
* **Pulos Condicionais:** Pular validadores (ex: 3 e 5) se um validador inicial (ex: 1) falhar.
* **Timeouts:** Cada validador ter seu próprio timeout.

### Padrão Escolhido: Chain of Responsibility (Cadeia de Responsabilidade)

O padrão **Chain of Responsibility** é um padrão comportamental que passa uma solicitação ao longo de uma cadeia de "handlers" (manipuladores).

### justificativa da Escolha

1.  **Arquitetura em Cadeia:** O padrão é a escolha natural para um *pipeline* de processamento onde um objeto (o documento) é "entregue" de um passo para o outro.
2.  **Decisão de Design (Gerenciador Central):** Em vez de uma cadeia simples (onde cada validador conhece o "próximo"), foi implementado um **Gerenciador de Cadeia** (`ValidacaoProcessor`). Esta foi uma decisão de design crucial, pois os requisitos complexos (Circuit Breaker, Rollback) são *transversais* e não podem ser gerenciados por um único validador.
3.  **Implementação dos Requisitos Complexos:**
    * **Rollback:** A interface `IValidator` define um método `rollback()`. O `ValidacaoProcessor` mantém uma lista dos validadores que já executaram e, em caso de falha, itera essa lista **ao contrário**, chamando `rollback()` em cada um (permitindo ao `DuplicidadeDBValidator` desfazer sua inserção).
    * **Circuit Breaker e Pulos:** O `ValidacaoProcessor` (e não os validadores) mantém a contagem de falhas (`failureCount`) e o status (`falhaAnteriorDetectada`) para decidir se deve interromper a cadeia ou pular um validador.
    * **Timeouts:** A interface `IValidator` define `getTimeoutSeconds()`, e o `ValidacaoProcessor` usa um `ExecutorService` para impor esse limite em cada chamada `validate()`.



---

## 🛠️ Como Executar

Cada questão está em seu próprio pacote Java (`Questao1`, `Questao2`, `Questao3`, `Questao4`).

1.  **Pré-requisito:** Certifique-se de ter o JDK (Java Development Kit) instalado.
2.  **Compilar e Executar (via terminal):**

    Navegue até o diretório raiz do projeto e execute os seguintes comandos para a questão desejada:

    ```bash
    # Exemplo para a Questão 1
    javac Questao1/*.java
    java Questao1.Main

    # Exemplo para a Questão 4
    javac Questao4/*.java
    java Questao4.Main
    ```
3.  **Via IDE (VSCode, IntelliJ, Eclipse):**
    * Abra o projeto.
    * Navegue até o pacote da questão (ex: `Questao4`).
    * Encontre e execute o arquivo `Main.java`.
