/**
 * Decisão de Design:
 * Criei um objeto de Contexto para encapsular todos os parâmetros financeiros.
 * Isso evita passar múltiplos argumentos para os métodos da estratégia,
 * tornando a interface mais limpa e fácil de estender.
 *
 * Se novos parâmetros forem necessários (ex: taxa de juros),
 * basta adicioná-los a esta classe, sem quebrar as assinaturas
 * da interface da estratégia.
 */
public class FinancialContext {
    double portfolioValue;
    double volatility;
    int timeHorizonDays;

    // Construtor e Getters/Setters (omitidos para brevidade)
}