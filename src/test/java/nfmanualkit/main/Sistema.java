package nfmanualkit.main;

import java.io.File;

public class Sistema {
    public static String ATUALIZACAO_TECNICA = "Manual de Orientação do Contribuinte - versão 6.00";

    public static String PATH_CONF = getTmp() + File.separator + "conf.properties";

    private static String getTmp() {
        String os = System.getProperty("os.name").toLowerCase();
        String tempDir;

        if (os.contains("win")) {
            tempDir = System.getenv("TEMP");

        } else if (os.contains("mac")) {
            tempDir = System.getProperty("java.io.tmpdir");

        } else {
            tempDir = System.getProperty("java.io.tmpdir");
        }

        return tempDir;
    }
}
