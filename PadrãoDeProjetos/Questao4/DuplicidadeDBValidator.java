public class DuplicidadeDBValidator implements IValidator {
    private boolean inserido = false; // Simula estado

    @Override
    public ValidationResult validate(DocumentoFiscal doc) {
        // Lógica dummy: simula inserção em tabela temporária
        System.out.println("  [DuplicidadeDB] Inserindo NFe " + doc.getNumeroNota() + " em tabela de verificação...");
        this.inserido = true;
        return new ValidationResult("DuplicidadeDB", true, "Nenhuma duplicidade encontrada.");
    }

    /**
     * Decisão de Design (Implementação de Rollback):
     * Este método será chamado pelo Processador se qualquer
     * validador SUBSEQUENTE (como o da SEFAZ) falhar.
     */
    @Override
    public void rollback(DocumentoFiscal doc) {
        if (this.inserido) {
            System.out.println("  [DuplicidadeDB - ROLLBACK] Removendo NFe " + doc.getNumeroNota() + " da tabela.");
            this.inserido = false;
        }
    }

    @Override
    public int getTimeoutSeconds() { return 4; }
}