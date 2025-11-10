public class SefazValidator implements IValidator {
    @Override
    public ValidationResult validate(DocumentoFiscal doc) {
        // Lógica dummy - Simula falha de comunicação com SEFAZ
        if (doc.getNumeroNota().equals("999")) {
             return new ValidationResult("SEFAZ", false, "Lote 999 Rejeitado pela SEFAZ. (Erro de simulação)");
        }
        return new ValidationResult("SEFAZ", true, "Lote aprovado pela SEFAZ.");
    }
    @Override
    public int getTimeoutSeconds() { return 10; }
}