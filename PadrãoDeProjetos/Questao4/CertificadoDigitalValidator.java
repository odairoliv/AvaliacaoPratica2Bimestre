public class CertificadoDigitalValidator implements IValidator {
    @Override
    public ValidationResult validate(DocumentoFiscal doc) {
        // Lógica dummy
        return new ValidationResult("Certificado", true, "Assinatura válida, LCR OK.");
    }
    @Override
    public int getTimeoutSeconds() { return 5; }
}