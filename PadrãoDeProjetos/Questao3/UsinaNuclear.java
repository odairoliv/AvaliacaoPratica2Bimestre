/**
 * Decisão de Design (Context Class):
 * Esta é a classe Contexto do padrão State.
 * Ela delega todo o comportamento para o objeto de estado atual.
 * Ela também fornece o método 'setState' para que os
 * estados possam transicionar a usina para um novo estado.
 */
public class UsinaNuclear {
    
    private IUsinaState estadoAtual;
    
    // Dados internos (simplificados)
    private double temperatura;
    // Usado para a regra "por mais de 30 segundos"
    private long timestampEntradaEmAlerta; 
    private boolean falhaResfriamento;

    public UsinaNuclear() {
        // Estado inicial
        this.estadoAtual = new EstadoDesligada();
        System.out.println("Usina criada. Estado inicial: DESLIGADA");
    }

    // Método crucial para o padrão State
    public void setState(IUsinaState novoEstado) {
        System.out.println("\n[TRANSIÇÃO] Mudando de " + 
                            estadoAtual.getClass().getSimpleName() + 
                            " para " + novoEstado.getClass().getSimpleName());
        this.estadoAtual = novoEstado;
    }

    // Métodos delegados ao estado atual
    public void ligar() { 
        estadoAtual.ligar(this); 
    }
    public void desligar() { 
        estadoAtual.desligar(this); 
    }
    public void entrarManutencao() { 
        estadoAtual.entrarManutencao(this); 
    }
    public void sairManutencao() { 
        estadoAtual.sairManutencao(this); 
    }
    
    /**
     * Simula a falha do sistema de resfriamento
     */
    public void simularFalhaResfriamento() {
        System.out.println("\n[EVENTO] *** FALHA NO SISTEMA DE RESFRIAMENTO ***");
        this.falhaResfriamento = true;
        // Notifica o estado atual sobre o evento
        estadoAtual.relatarFalhaResfriamento(this);
    }

    /**
     * Simula a passagem do tempo e a leitura de sensores
     */
    public void simularMonitoramento(double novaTemperatura) {
        System.out.println("[MONITOR] Temperatura atual: " + novaTemperatura + "°C");
        this.temperatura = novaTemperatura;
        // Delega a verificação das regras para o estado atual
        estadoAtual.monitorar(this);
    }
    
    // Getters e Setters para os estados
    public double getTemperatura() { return temperatura; }
    public boolean isFalhaResfriamento() { return falhaResfriamento; }
    
    public void setTimestampEntradaEmAlerta(long timestamp) {
        this.timestampEntradaEmAlerta = timestamp;
    }
    public long getTimestampEntradaEmAlerta() {
        return this.timestampEntradaEmAlerta;
    }
}