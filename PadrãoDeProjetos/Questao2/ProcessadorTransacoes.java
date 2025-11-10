/**
 * Decisão de Design: Esta é a "Target Interface".
 *
 * Define a interface moderna e limpa que o nosso sistema cliente
 * (o 'Client') espera usar.
 */
public interface ProcessadorTransacoes {
    /**
     * Método moderno para autorizar uma transação.
     * @param cartao Número do cartão.
     * @param valor Valor da transação.
     * @param moeda Moeda (ex: "BRL", "USD").
     * @return Um objeto de resposta moderno e limpo.
     */
    RespostaAutorizacao autorizar(String cartao, double valor, String moeda);
}