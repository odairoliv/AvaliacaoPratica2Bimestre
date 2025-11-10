import java.util.HashMap;

/**
 * Decisão de Design: Esta é a classe "Adaptee".
 *
 * Representa o sistema legado com sua interface incompatível.
 * Não podemos alterar este código.
 * Ele espera um HashMap complexo e retorna um HashMap como resposta.
 */
public class SistemaBancarioLegado {

    /**
     * O método legado que processa a transação.
     * @param parametros Um HashMap com dados em formato obsoleto.
     * @return Um HashMap com a resposta do legado.
     */
    public HashMap<String, Object> processarTransacao(HashMap<String, Object> parametros) {
        System.out.println("[LEGADO] Recebendo parâmetros: " + parametros);

        // Simulação da lógica do legado
        HashMap<String, Object> respostaLegada = new HashMap<>();

        // Validação dos campos obrigatórios
        if (!parametros.containsKey("CARTAO_NUM") ||
            !parametros.containsKey("VALOR_TOTAL") ||
            !parametros.containsKey("COD_MOEDA") ||
            !parametros.containsKey("CODIGO_LOJA")) { // Campo obrigatório
            
            respostaLegada.put("status_code", 400); // Bad Request
            respostaLegada.put("mensagem_legado", "DADOS FALTANTES");
            return respostaLegada;
        }

        // Lógica de simulação
        double valor = (double) parametros.get("VALOR_TOTAL");
        if (valor > 1000) {
            respostaLegada.put("status_code", 501); // Erro
            respostaLegada.put("mensagem_legado", "VALOR MUITO ALTO. RECUSADO.");
        } else {
            respostaLegada.put("status_code", 200); // OK
            respostaLegada.put("mensagem_legado", "APROVADO");
            respostaLegada.put("id_transacao_legado", "LEGACY_ID_" + Math.random());
        }

        System.out.println("[LEGADO] Enviando resposta: " + respostaLegada);
        return respostaLegada;
    }
}