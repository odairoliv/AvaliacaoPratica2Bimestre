/**
 * Decisão de Design (Concrete State):
 * Lida com ALERTA_AMARELO e a regra para ALERTA_VERMELHO.
 * Permite transição bidirecional (de volta ao normal).
 */
public class EstadoAlertaAmarelo implements IUsinaState {

    @Override
    public void monitorar(UsinaNuclear usina) {
        // Regra Bidirecional: Volta ao normal se a temperatura baixar
        if (usina.getTemperatura() <= 300) {
            usina.setState(new EstadoOperacaoNormal());
            return;
        }

        // Regra: ALERTA_AMARELO → ALERTA_VERMELHO: se temp > 400°C por > 30 seg
        if (usina.getTemperatura() > 400) {
            long tempoEmAlerta = (System.currentTimeMillis() - usina.getTimestampEntradaEmAlerta());
            
            // Convertendo para segundos (dividindo por 1000)
            if (tempoEmAlerta > 30000) { // 30 segundos
                usina.setState(new EstadoAlertaVermelho());
            }
        } else {
            // Reseta o timer se a temp baixar de 400 (mas continuar > 300)
            usina.setTimestampEntradaEmAlerta(System.currentTimeMillis());
        }
    }

    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) {
        // Regra: Falha de resfriamento em Amarelo leva direto a Emergência
        // (Isso é uma interpretação, poderia ir para Vermelho também)
        System.out.println("[LOG] Falha de resfriamento em ALERTA AMARELO. Escalando para EMERGÊNCIA.");
        usina.setState(new EstadoEmergencia());
    }
    
    @Override
    public void desligar(UsinaNuclear usina) {
        usina.setState(new EstadoDesligada());
    }

    @Override
    public void ligar(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina já está ligada."); 
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