/**
 * Decisão de Design (Strategy Concreta 2):
 * Implementação do algoritmo "Expected Shortfall".
 *
 * Princípio SOLID (SRP): Esta classe tem uma única
 * responsabilidade: calcular o ES.
 */
public class ExpectedShortfallStrategy implements IRiskAlgorithmStrategy {
    @Override
    public String calculateRisk(FinancialContext context) {
        // Cálculo dummy
        return "CÁLCULO DUMMY (Expected Shortfall): Perda esperada de " +
               (context.portfolioValue * context.volatility * 0.15) + " em cenário de cauda.";
    }
}