/**
 * Decisão de Design (State Interface):
 * Define todas as ações e eventos que podem ocorrer na usina.
 * Cada classe de estado concreta implementará esta interface
 * para definir o comportamento específico daquele estado.
 */
public interface IUsinaState {
    // Ações do usuário/sistema
    void ligar(UsinaNuclear usina);
    void desligar(UsinaNuclear usina);
    void relatarFalhaResfriamento(UsinaNuclear usina);
    void entrarManutencao(UsinaNuclear usina);
    void sairManutencao(UsinaNuclear usina);

    /**
     * Método chave para transições baseadas em sensores.
     * Cada estado verificará as condições e pedirá
     * a transição, se necessário.
     */
    void monitorar(UsinaNuclear usina);
}