public class RegrasFiscaisValidator implements IValidator {
    @Override
    public ValidationResult validate(DocumentoFiscal doc) {
        // Lógica dummy de cálculo de imposto
        return new ValidationResult("RegrasFiscais", true, "Cálculo de ICMS e IPI bate.");
    }
    @Override
    public int getTimeoutSeconds() { return 3; }
}