public class XMLSchemaValidator implements IValidator {
    @Override
    public ValidationResult validate(DocumentoFiscal doc) {
        // Lógica dummy
        if (!doc.getXmlContent().startsWith("<xml>")) {
            return new ValidationResult("XMLSchema", false, "XML mal formado.");
        }
        return new ValidationResult("XMLSchema", true, "Schema XSD OK.");
    }
    @Override
    public int getTimeoutSeconds() { return 2; }
}