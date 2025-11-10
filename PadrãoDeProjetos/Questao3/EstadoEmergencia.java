/**
 * Decisão de Design (Concrete State):
 * Estado final de emergência.
 * Praticamente bloqueia todas as outras ações.
 */
public class EstadoEmergencia implements IUsinaState {
    
    public EstadoEmergencia() {
        System.out.println("*************************************************");
        System.out.println("*** EMERGÊNCIA! EVACUAR ÁREA! ***");
        System.out.println("*************************************************");
    }

    @Override
    public void ligar(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Ação bloqueada."); 
    }
    
    @Override
    public void desligar(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Desligamento automático em progresso."); 
    }
    
    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Múltiplas falhas detectadas."); 
    }
    
    @Override
    public void entrarManutencao(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Ação bloqueada."); 
    }
    
    @Override
    public void sairManutencao(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Ação bloqueada."); 
    }
    
    @Override
    public void monitorar(UsinaNuclear usina) { 
        System.out.println("[EMERGÊNCIA] Monitorando falha catastrófica."); 
    }
}