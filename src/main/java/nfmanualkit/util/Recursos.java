package nfmanualkit.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
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

    private static String nomeArquivo = "rotulos_pt_BR.properties";

    private Recursos(){}

    /**
     * Retorna o valor de um recurso pelo nome(nomeRecurso). <br>
     * Se for disparado alguma exceção o valor retornado será: "<nomeRecurso> não encontrado".
     *
     * @param nomeRecurso nome do recurso.
     * @return String com o valor do recurso.
     */
    public static String get(String nomeRecurso) {
        String recurso;
        try {
            final InputStream isArquivo = Recursos.class.getClassLoader().getResourceAsStream(nomeArquivo);
            final InputStreamReader isr = new InputStreamReader(isArquivo, StandardCharsets.UTF_8);
            final Properties properties = new Properties();

            properties.load(isr);
            recurso = properties.getProperty(nomeRecurso);

        } catch (Exception e){
            e.printStackTrace();
            recurso = nomeRecurso + " não encontrado.";
        }

        return recurso;
    }
}
