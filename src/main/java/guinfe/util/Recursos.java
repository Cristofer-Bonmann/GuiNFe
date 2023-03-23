package guinfe.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>
 * Classe responsável por acessar o arquivo 'torulos_pt_BR' do diretório 'resources' e retornar seus valores.
 * </p>
 * <p>Como usar:</p>
 * <pre>
 *     final String conteudoRecurso = Recursos.get("nome_recurso");
 * </pre>
 */
public class Recursos {

    private Recursos(){}

    /**
     * Retorna o valor de um recurso pelo nome(nomeRecurso). <br>
     *
     * @param nomeRecurso nome do recurso.
     * @return String com o valor do recurso.
     */
    private static String getRecurso(String nomeRecurso) {
        Locale.setDefault(new Locale("pt", "BR"));
        final ResourceBundle bundle = ResourceBundle.getBundle("rotulos", new UTF8Control());

        return bundle.getString(nomeRecurso);
    }

    /**
     * Retorna o valor de um recurso pelo nome(nomeRecurso). <br>
     *
     * @param nomeRecurso nome do recurso.
     * @return String com o valor do recurso.
     */
    public static String get(String nomeRecurso) {
        return getRecurso(nomeRecurso);
    }

    /**
     * Recebe o nomedo recurso que será retornado e o parâmetro que será adicionado no valor desse retorno. Ex.:<br>
     * <pre>
     *     //no arquivo recursos.propierties:
     *     msg_001 = Apenas uma mensagem de:%s
     *
     *     //no código fonte:
     *     final String msg = Recursos.get("msg_001", "João");
     *     //será retornado a String: Apenas uma mensagem de:João
     * </pre>
     *
     * @param nomeRecurso nome do recurso.
     * @param parametro valor que será substituído na variável de controle %s ao retornar o recurso.
     * @return String com o valor do recurso.
     */
    public static String get(String nomeRecurso, String parametro) {
        String recurso;

        recurso = getRecurso(nomeRecurso);
        recurso = String.format(recurso, parametro);

        return recurso;
    }
}
