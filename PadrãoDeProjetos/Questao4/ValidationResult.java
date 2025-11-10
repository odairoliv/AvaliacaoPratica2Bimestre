public class ValidationResult {
    private boolean sucesso;
    private String mensagem;
    private String validadorNome;

    public ValidationResult(String validadorNome, boolean sucesso, String mensagem) {
        this.validadorNome = validadorNome;
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public boolean isSucesso() { return sucesso; }
    public String getMensagem() { return mensagem; }
    public String getValidadorNome() { return validadorNome; }

    @Override
    public String toString() {
        return "  [" + validadorNome + "] " + (sucesso ? "SUCESSO" : "FALHA") + ": " + mensagem;
    }
}