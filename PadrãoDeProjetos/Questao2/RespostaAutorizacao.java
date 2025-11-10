/**
 * Decisão de Design: Objeto de Resposta Moderno.
 *
 * Parte da solução "bidirecional". Em vez de retornar um HashMap
 * complexo para o cliente, o Adapter traduzirá a resposta
 * legada para este objeto simples.
 */
public class RespostaAutorizacao {
    private boolean aprovado;
    private String mensagemCliente;

    public RespostaAutorizacao(boolean aprovado, String mensagemCliente) {
        this.aprovado = aprovado;
        this.mensagemCliente = mensagemCliente;
    }

    // Getters
    public boolean isAprovado() { return aprovado; }
    public String getMensagemCliente() { return mensagemCliente; }

    @Override
    public String toString() {
        return "RespostaAutorizacao{" +
               "aprovado=" + aprovado +
               ", mensagemCliente='" + mensagemCliente + '\'' +
               '}';
    }
}