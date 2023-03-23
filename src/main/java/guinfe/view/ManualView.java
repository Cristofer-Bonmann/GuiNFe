package guinfe.view;

import guinfe.entity.SchemaNfe;
import guinfe.enumeracao.EFiltro;

import java.util.List;

public interface ManualView {

    void exibir(List<SchemaNfe> lista);

    void setSelectedEFiltro(EFiltro eFiltro);

    EFiltro getSelectedEFiltro();

    boolean isMatchCase();

    boolean isOcorrenciaPalavra();

    String getFiltro();

    void setMatchCaseSelected(boolean matchCase);

    void setOcorrenciaLetraSelected(boolean ocorrenciaLetra);

    void notificar(String msg);
}
