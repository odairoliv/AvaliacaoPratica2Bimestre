/**
 * Decisão de Design (Concrete State - Requisito "Manutenção"):
 * Implementa o modo de manutenção que "sobreescreve"
 * os estados normais.
 * Ele ignora a maioria dos comandos operacionais e de monitoramento.
 */
public class EstadoManutencao implements IUsinaState {

    @Override
    public void sairManutencao(UsinaNuclear usina) {
        // Regra: Manutenção só pode sair para o estado DESLIGADA.
        usina.setState(new EstadoDesligada());
    }

    // "Sobreescreve" os comportamentos normais
    @Override
    public void ligar(UsinaNuclear usina) { 
        System.out.println("[MANUTENÇÃO] Ação 'ligar' bloqueada."); 
    }
    
    @Override
    public void desligar(UsinaNuclear usina) { 
        System.out.println("[MANUTENÇÃO] Ação 'desligar' bloqueada."); 
    }
    
    @Override
    public void relatarFalhaResfriamento(UsinaNuclear usina) { 
        System.out.println("[MANUTENÇÃO] Sensores desativados, falha ignorada."); 
    }
    
    @Override
    public void entrarManutencao(UsinaNuclear usina) { 
        System.out.println("[MANUTENÇÃO] Usina já está em manutenção."); 
    }

    @Override
    public void monitorar(UsinaNuclear usina) { 
        /* Sensores em calibração, monitoramento operacional suspenso */ 
    }
}