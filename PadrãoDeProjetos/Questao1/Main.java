/**
 * Classe Cliente  (simulação) que utiliza o processador.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Criar o contexto financeiro complexo
        FinancialContext context = new FinancialContext();
        context.portfolioValue = 5000000.0;
        context.volatility = 0.25;
        context.timeHorizonDays = 10;

        // 2. Iniciar o processador com a primeira estratégia (Value at Risk)
        RiskAnalysisProcessor processor = new RiskAnalysisProcessor(
            new ValueAtRiskStrategy(),
            context
        );

        System.out.println("Executando primeira análise:");
        System.out.println(processor.executeRiskAnalysis());
        System.out.println("-----------------------------------------------------");

        // 3. Simulação de "mudança de necessidade de negócio"
        // Trocando o algoritmo em tempo de execução para Expected Shortfall
        processor.setAlgorithmStrategy(new ExpectedShortfallStrategy());
        System.out.println("Executando segunda análise:");
        System.out.println(processor.executeRiskAnalysis());
        System.out.println("-----------------------------------------------------");

        // 4. Nova troca em tempo de execução
        processor.setAlgorithmStrategy(new StressTestingStrategy());
        System.out.println("Executando terceira análise:");
        System.out.println(processor.executeRiskAnalysis());

        //5. Vazia para verificação e erro
        processor.setAlgorithmStrategy(null);
        System.out.println("Executando quarta análise:");
        System.out.println(processor.executeRiskAnalysis());
    }
}