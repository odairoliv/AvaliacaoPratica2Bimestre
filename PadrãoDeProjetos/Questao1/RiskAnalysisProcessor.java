/**
 * Decisão de Design (Classe de Contexto ):
 * Esta é a classe 'Contexto' do Padrão Strategy.
 * Ela mantém uma referência para a interface da estratégia 
 * e delega a execução do algoritmo para o objeto de estratégia.
 *
 * Justificativa do Padrão (Strategy):
 * O Padrão Strategy foi escolhido para cumprir os requisitos de:
 * 1. Intercambialidade: O método 'setAlgorithmStrategy' permite trocar
 * o algoritmo em tempo de execução[cite: 6, 19].
 * 2. Desacoplamento: Este processador (Cliente/Contexto) não conhece a
 * implementação dos algoritmos; ele apenas usa a interface comum.
 *
 * Princípios SOLID Aplicados:
 * - Open-Closed (OCP): Esta classe está fechada para modificação.
 * Para adicionar um novo cálculo, não mexemos aqui, apenas criamos
 * uma nova classe 'Strategy'.
 * - Dependency Inversion (DIP)[cite: 611]: Esta classe depende da
 * abstração 'IRiskAlgorithmStrategy', e não de implementações concretas.
 */
public class RiskAnalysisProcessor {

    private IRiskAlgorithmStrategy algorithmStrategy;
    private FinancialContext financialContext;

    /**
     * O contexto é inicializado com um contexto financeiro
     * e uma estratégia inicial.
     */
    public RiskAnalysisProcessor(IRiskAlgorithmStrategy initialStrategy, FinancialContext context) {
        this.algorithmStrategy = initialStrategy;
        this.financialContext = context;
    }

    /**
     * Decisão de Design:
     * Permite ao cliente alterar a estratégia (o algoritmo)
     * dinamicamente em tempo de execução.
     */
    public void setAlgorithmStrategy(IRiskAlgorithmStrategy algorithmStrategy) {
        System.out.println("--- [SISTEMA] Trocando algoritmo de análise de risco ---");
        this.algorithmStrategy = algorithmStrategy;
    }

    /**
     * O 'Contexto' delega a execução para a 'Strategy' atual.
     * O cliente não precisa saber qual algoritmo está sendo executado.
     */
    public String executeRiskAnalysis() {
        if (algorithmStrategy == null) {
            return "ERRO: Nenhum algoritmo de risco selecionado.";
        }
        // Delegação da execução para o objeto de estratégia 
        return this.algorithmStrategy.calculateRisk(this.financialContext);
    }
}