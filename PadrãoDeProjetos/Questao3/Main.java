public class Main {
    
    // Método auxiliar para simular a passagem de tempo
    public static void sleep(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UsinaNuclear usina = new UsinaNuclear();
        
        System.out.println("\n--- SIMULAÇÃO 1: Manutenção ---");
        usina.entrarManutencao();
        usina.ligar(); // Deve ser bloqueado
        usina.simularMonitoramento(500); // Deve ser ignorado
        usina.sairManutencao();
        
        System.out.println("\n--- SIMULAÇÃO 2: Operação Normal -> Amarelo -> Vermelho ---");
        usina.ligar();
        usina.simularMonitoramento(250); // Normal
        usina.simularMonitoramento(310); // Transição para Amarelo
        
        System.out.println("\n...Esperando 10 segundos (Regra dos 30s não deve disparar)...");
        sleep(10); 
        usina.simularMonitoramento(350); // Ainda em Amarelo
        
        System.out.println("\n...Temperatura subindo muito...");
        usina.simularMonitoramento(410); // Acima de 400, timer da regra começa
        
        System.out.println("\n...Esperando 35 segundos (Regra dos 30s VAI disparar)...");
        sleep(35);
        usina.simularMonitoramento(415); // Dispara ALERTA VERMELHO

        System.out.println("\n--- SIMULAÇÃO 3: Vermelho -> Emergência ---");
        usina.simularMonitoramento(390); // Temp baixou, deve voltar para Amarelo
        usina.simularMonitoramento(420); // Volta para Vermelho
        
        // A regra fatal
        usina.simularFalhaResfriamento(); // Transição para EMERGÊNCIA
        
        // Tentativas inúteis
        usina.ligar();
        usina.desligar();
    }
}