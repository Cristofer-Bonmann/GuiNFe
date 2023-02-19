package nfmanualkit.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.ResourceBundle;

// TODO: 18/02/2023 inserir doc
public class Recursos {

    private static String nomeArquivo = "rotulos_pt_BR.properties";

    private Recursos(){}

    // TODO: 18/02/2023 inserir doc
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
