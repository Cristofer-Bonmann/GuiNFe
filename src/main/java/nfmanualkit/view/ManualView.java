package nfmanualkit.view;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;

import java.util.List;

public interface ManualView {

  void exibir(List<SchemaNfe> lista);

  void setSelectedEFiltro(EFiltro eFiltro);
}
