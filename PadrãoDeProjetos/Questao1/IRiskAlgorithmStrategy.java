/**
 * Decisão de Design (Interface Strategy):
 * Esta é a interface 'Strategy'.
 * Ela define o contrato comum (o método 'calculateRisk')
 * que todas as implementações concretas de algoritmo  devem fornecer.
 *
 * Ela depende da abstração 'FinancialContext' para receber os dados.
 */
public interface IRiskAlgorithmStrategy {
    /**
     * Executa o cálculo de risco com base no contexto fornecido.
     * @param context O objeto contendo os parâmetros financeiros.
     * @return Uma string com o resultado do cálculo (dummy).
     */
    String calculateRisk(FinancialContext context);
}