import java.util.HashMap;

/**
 * Decisão de Design: Esta é a classe "Adapter". 
 *
 * Esta classe implementa a interface "Target" (ProcessadorTransacoes)
 * e contém uma instância da classe "Adaptee" (SistemaBancarioLegado).
 *
 * Sua única responsabilidade (SRP) é traduzir as chamadas
 * do formato moderno para o formato legado, e traduzir as respostas
 * do formato legado de volta para o formato moderno.
 */
public class TransacaoAdapter implements ProcessadorTransacoes {

    // O Adapter "envolve" (wraps) o sistema legado.
    private SistemaBancarioLegado sistemaLegado;

    public TransacaoAdapter(SistemaBancarioLegado sistemaLegado) {
        this.sistemaLegado = sistemaLegado;
    }

    @Override
    public RespostaAutorizacao autorizar(String cartao, double valor, String moeda) {
        
        // 1. TRADUZIR A REQUISIÇÃO (Moderno -> Legado)
        HashMap<String, Object> parametrosLegados = new HashMap<>();
        parametrosLegados.put("CARTAO_NUM", cartao);
        parametrosLegados.put("VALOR_TOTAL", valor);

        // 2. LIDAR COM RESTRIÇÕES (Mapeamento de Moeda)
        // A interface moderna envia "BRL", o legado espera 3.
        parametrosLegados.put("COD_MOEDA", converterMoedaParaCodigo(moeda));

        // 3. LIDAR COM CAMPOS OBRIGATÓRIOS FALTANTES
        // A interface moderna não sabe sobre "CODIGO_LOJA",
        // mas o legado exige. O Adapter injeta esse dado.
        parametrosLegados.put("CODIGO_LOJA", "LOJA_ONLINE_42");

        // 4. CHAMAR O MÉTODO LEGADO
        HashMap<String, Object> respostaLegada = sistemaLegado.processarTransacao(parametrosLegados);

        // 5. TRADUZIR A RESPOSTA (Legado -> Moderno)
        // Cumprindo o requisito de "bidirecional".
        return converterRespostaParaModerno(respostaLegada);
    }

    /**
     * Método auxiliar privado para a lógica de tradução.
     * (Restrição: USD=1, EUR=2, BRL=3)
     */
    private int converterMoedaParaCodigo(String moeda) {
        switch (moeda.toUpperCase()) {
            case "USD": return 1;
            case "EUR": return 2;
            case "BRL": return 3;
            default: return 0; // Código de erro/desconhecido
        }
    }

    /**
     * Método auxiliar privado para a tradução "bidirecional" da resposta.
     */
    private RespostaAutorizacao converterRespostaParaModerno(HashMap<String, Object> respostaLegada) {
        int statusCode = (int) respostaLegada.get("status_code");
        String mensagem = (String) respostaLegada.get("mensagem_legado");

        boolean aprovado = (statusCode == 200);
        
        return new RespostaAutorizacao(aprovado, "Status: " + mensagem);
    }
}