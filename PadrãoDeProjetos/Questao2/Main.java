/**
 * Classe Cliente (simulação) que utiliza o processador.
 *
 * Decisão de Design (DIP): O Cliente depende da abstração
 * "ProcessadorTransacoes", e não de uma implementação concreta.
 * Ele não sabe (e não se importa) que está falando com um Adapter.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Criamos a instância do sistema legado que não podemos mudar.
        SistemaBancarioLegado sistemaLegado = new SistemaBancarioLegado();

        // 2. Criamos nosso Adapter e injetamos o sistema legado nele
        ProcessadorTransacoes processador = new TransacaoAdapter(sistemaLegado);

        // 3. O Cliente usa a interface moderna.
        System.out.println("[CLIENTE] Enviando transação de R$ 150,00...");
        RespostaAutorizacao resp1 = processador.autorizar("1234-5678-9012-3456", 150.0, "BRL");
        System.out.println("[CLIENTE] Resposta recebida: " + resp1);

        System.out.println("---");

        // 4. Teste de falha (valor alto)
        System.out.println("[CLIENTE] Enviando transação de R$ 1500,00...");
        RespostaAutorizacao resp2 = processador.autorizar("9876-5432-1098-7654", 1500.0, "USD");
        System.out.println("[CLIENTE] Resposta recebida: " + resp2);
    }
}