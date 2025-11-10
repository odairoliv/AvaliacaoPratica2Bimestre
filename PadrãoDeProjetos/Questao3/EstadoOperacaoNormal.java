/**
 * Decisão de Design (Concrete State):
 * Lida com a operação normal e a regra de transição
 * para ALERTA_AMARELO.
 */
public class EstadoOperacaoNormal implements IUsinaState {
    @Override
    public void desligar(UsinaNuclear usina) {
        usina.setState(new EstadoDesligada());
    }

    @Override
    public void monitorar(UsinaNuclear usina) {
        // Regra: OPERACAO_NORMAL → ALERTA_AMARELO: se temperatura > 300°C
        if (usina.getTemperatura() > 300) {
            usina.setState(new EstadoAlertaAmarelo());
            // Define o timestamp para a próxima regra
            usina.setTimestampEntradaEmAlerta(System.currentTimeMillis()); 
        }
    }
    
    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) {
        System.out.println("[LOG] Falha de resfriamento detectada. Elevando para ALERTA VERMELHO.");
        usina.setState(new EstadoAlertaVermelho());
    }
    
    @Override
    public void ligar(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina já está em operação normal."); 
    }

    @Override
    public void entrarManutencao(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Impossível entrar em manutenção. Desligue a usina primeiro."); 
    }

    @Override
    public void sairManutencao(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina não está em manutenção."); 
    }
}