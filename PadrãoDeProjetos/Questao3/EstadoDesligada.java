/**
 * Decisão de Design (Concrete State):
 * Implementa o comportamento quando a usina está desligada.
 * Só permite as transições 'ligar' e 'entrarManutencao'.
 */
public class EstadoDesligada implements IUsinaState {
    @Override
    public void ligar(UsinaNuclear usina) {
        usina.setState(new EstadoOperacaoNormal());
    }

    @Override
    public void entrarManutencao(UsinaNuclear usina) {
        usina.setState(new EstadoManutencao());
    }

    // Ignora outras ações
    @Override
    public void desligar(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina já está desligada."); 
    }
    
    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) { 
        System.out.println("[LOG] Falha de resfriamento reportada, mas usina está desligada."); 
    }
    
    @Override
    public void sairManutencao(UsinaNuclear usina) { 
        System.out.println("[AÇÃO] Usina não está em manutenção."); 
    }
    
    @Override
    public void monitorar(UsinaNuclear usina) { 
        /* Sensores inativos */ 
    }
}