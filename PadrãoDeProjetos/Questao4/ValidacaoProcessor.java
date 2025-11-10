import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Decisão de Design (Gerenciador da Cadeia):
 * Esta classe implementa o Padrão Chain of Responsibility.
 * Ela atua como um Contexto/Mediador que gerencia a
 * execução da cadeia.
 *
 * Isso é crucial para implementar requisitos transversais
 * como Circuit Breaker, Rollback e Skips Condicionais,
 * que um CoR de "linked-list" simples não lidaria bem.
 */
public class ValidacaoProcessor {
    
    private final List<IValidator> cadeiaDeValidadores = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void addValidator(IValidator validator) {
        this.cadeiaDeValidadores.add(validator);
    }

    public List<ValidationResult> processarCadeia(DocumentoFiscal doc) {
        List<ValidationResult> resultados = new ArrayList<>();
        List<IValidator> executados = new ArrayList<>(); // Para rollback
        
        int failureCount = 0;
        boolean falhaAnteriorDetectada = false;

        System.out.println("--- Iniciando processamento da cadeia para NFe: " + doc.getNumeroNota() + " ---");

        for (IValidator validador : cadeiaDeValidadores) {
            
            // Requisito: Pular 3 (Fiscal) e 5 (SEFAZ) se anteriores falharam
            if (falhaAnteriorDetectada && 
                (validador instanceof RegrasFiscaisValidator || validador instanceof SefazValidator)) {
                System.out.println("  [PROCESSADOR] Pulando " + validador.getClass().getSimpleName() + " devido à falha anterior.");
                continue;
            }

            // Adiciona à lista de rollback ANTES de executar
            executados.add(validador);
            
            ValidationResult result = null;
            
            // Requisito: Timeout individual
            Future<ValidationResult> future = executor.submit(() -> validador.validate(doc));
            try {
                result = future.get(validador.getTimeoutSeconds(), TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                result = new ValidationResult(validador.getClass().getSimpleName(), false, "FALHA: Timeout de " + validador.getTimeoutSeconds() + "s excedido.");
                future.cancel(true); // Interrompe a thread
            } catch (Exception e) {
                result = new ValidationResult(validador.getClass().getSimpleName(), false, "FALHA: Erro interno. " + e.getMessage());
            }
            
            resultados.add(result);

            if (!result.isSucesso()) {
                failureCount++;
                falhaAnteriorDetectada = true;
                
                // Requisito: Circuit Breaker
                if (failureCount >= 3) {
                    System.out.println("[PROCESSADOR - CIRCUIT BREAKER] 3 falhas detectadas. Interrompendo cadeia.");
                    break; // Sai do loop
                }
            }
        }

        // Requisito: Rollback
        if (falhaAnteriorDetectada) {
            System.out.println("[PROCESSADOR] Falha na cadeia detectada. Iniciando rollback reverso...");
            // Itera em ordem reversa para o rollback
            for (int i = executados.size() - 1; i >= 0; i--) {
                executados.get(i).rollback(doc);
            }
        }
        
        System.out.println("--- Processamento da cadeia finalizado. ---");
        return resultados;
    }
    
    // É importante desligar o executor
    public void shutdown() {
        executor.shutdown();
    }
}