public class DocumentoFiscal {
    private String xmlContent;
    private String numeroNota;
    
    public DocumentoFiscal(String xmlContent, String numeroNota) {
        this.xmlContent = xmlContent;
        this.numeroNota = numeroNota;
    }
    
    // Getters
    public String getXmlContent() { return xmlContent; }
    public String getNumeroNota() { return numeroNota; }
}