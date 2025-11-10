/**
 * Decisão de Design (Strategy Concreta 1):
 * Implementação do algoritmo "Value at Risk".
 *
 * Princípio SOLID (SRP): Esta classe tem uma única
 * responsabilidade: calcular o VaR.
 */
public class ValueAtRiskStrategy implements IRiskAlgorithmStrategy {
    @Override
    public String calculateRisk(FinancialContext context) {
        // Cálculo dummy
        return "CÁLCULO DUMMY (VaR): Risco de " + (context.portfolioValue * context.volatility * 0.1) +
               " calculado para " + context.timeHorizonDays + " dias.";
    }
}