/**
 * Decisão de Design (Concrete State):
 * Lida com ALERTA_VERMELHO.
 * Esta é a ÚNICA classe com lógica para transicionar para EMERGENCIA
 * baseada na regra específica.
 */
public class EstadoAlertaVermelho implements IUsinaState {
    
    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) {
        // Regra: ALERTA_VERMELHO → EMERGENCIA: se sistema de resfriamento falhar
        usina.setState(new EstadoEmergencia());
    }

    @Override
    public void monitorar(UsinaNuclear usina) {
        // Verificação redundante caso o evento 'relatarFalhaResfriamento' falhe
        if (usina.isFalhaResfriamento()) {
            usina.setState(new EstadoEmergencia());
        }
        
        // Regra bidirecional: Pode voltar para Amarelo
        if (usina.getTemperatura() <= 400) {
             usina.setState(new EstadoAlertaAmarelo());
             usina.setTimestampEntradaEmAlerta(System.currentTimeMillis());
        }
    }
    
    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("[AÇÃO] Iniciando procedimento de desligamento de emergência...");
        usina.setState(new EstadoDesligada());
    }

    @Override
    public void ligar(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Impossível. Usina em Alerta Vermelho."); 
    }

    @Override
    public void entrarManutencao(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Impossível. Usina em Alerta Vermelho."); 
    }

    @Override
    public void sairManutencao(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina não está em manutenção."); 
    }
}