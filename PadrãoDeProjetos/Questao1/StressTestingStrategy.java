/**
 * Decisão de Design (Strategy Concreta 3):
 * Implementação do algoritmo "Stress Testing".
 *
 * Princípio SOLID (SRP): Esta classe tem uma única
 * responsabilidade: realizar o Stress Test.
 */
public class StressTestingStrategy implements IRiskAlgorithmStrategy {
    @Override
    public String calculateRisk(FinancialContext context) {
        // Cálculo dummy
        return "CÁLCULO DUMMY (Stress Testing): Portfólio testado contra cenários de estresse. " +
               "Volatilidade usada: " + context.volatility;
    }
}