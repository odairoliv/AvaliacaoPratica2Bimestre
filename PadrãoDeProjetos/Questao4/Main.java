import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Configura o processador e a cadeia
        ValidacaoProcessor processor = new ValidacaoProcessor();
        processor.addValidator(new XMLSchemaValidator());
        processor.addValidator(new CertificadoDigitalValidator());
        processor.addValidator(new RegrasFiscaisValidator()); // (3)
        processor.addValidator(new DuplicidadeDBValidator()); // (4)
        processor.addValidator(new SefazValidator());        // (5)

        
        // --- Cenário 1: Tudo OK ---
        System.out.println("\nCENÁRIO 1: NOTA VÁLIDA (NFe 123)");
        DocumentoFiscal docOK = new DocumentoFiscal("<xml>...</xml>", "123");
        List<ValidationResult> resultadosOK = processor.processarCadeia(docOK);
        resultadosOK.forEach(System.out::println);
        
        
        // --- Cenário 2: Falha na SEFAZ (Validador 5) e Teste de Rollback ---
        System.out.println("\nCENÁRIO 2: FALHA NA SEFAZ (NFe 999) - Testando Rollback");
        DocumentoFiscal docFalhaSefaz = new DocumentoFiscal("<xml>...</xml>", "999");
        List<ValidationResult> resultadosSefaz = processor.processarCadeia(docFalhaSefaz);
        resultadosSefaz.forEach(System.out::println);
        // Espera-se ver o "ROLLBACK" do validador 4 (DB)
        
        
        // --- Cenário 3: Falha no Início (XML) e Teste de Skip ---
        System.out.println("\nCENÁRIO 3: FALHA NO XML (NFe 456) - Testando Skip");
        DocumentoFiscal docFalhaXml = new DocumentoFiscal("INVALIDO", "456");
        List<ValidationResult> resultadosXml = processor.processarCadeia(docFalhaXml);
        resultadosXml.forEach(System.out::println);
        // Espera-se ver a mensagem "Pulando RegrasFiscais" e "Pulando SefazValidator"
        
        // --- Cenário 4: Teste de Circuit Breaker ---
        // (Para simular 3 falhas, preciso de validadores que falhem)
        // Por simplicidade, este teste é omitido, mas a lógica (failureCount >= 3)
        // está implementada no ValidacaoProcessor.

        
        // Desliga o pool de threads
        processor.shutdown();
    }
}