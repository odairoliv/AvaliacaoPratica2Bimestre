import java.util.concurrent.TimeUnit;

/**
 * Decisão de Design (Handler Interface):
 * Define o contrato para todos os validadores (Concrete Handlers).
 * Inclui métodos para 'rollback' e 'timeout' para
 * atender aos requisitos complexos da questão.
 */
public interface IValidator {
    
    /**
     * Executa a lógica de validação.
     */
    ValidationResult validate(DocumentoFiscal doc);

    /**
     * Decisão de Design (Rollback):
     * Permite que o processador da cadeia desfaça operações.
     * É um 'default' pois a maioria dos validadores não
     * modifica o estado e não precisa de rollback.
     */
    default void rollback(DocumentoFiscal doc) {
        // No-op por padrão
    }

    /**
     * Decisão de Design (Timeout):
     * Permite que cada validador defina seu próprio
     * timeout individual.
     */
    int getTimeoutSeconds();
}