package nfmanualkit.presenter;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;

import java.sql.SQLException;
import java.util.List;

public interface DaoPresenter {

  List<SchemaNfe> listar() throws SQLException;

  List<SchemaNfe> listar(EFiltro eFiltro, String filtro, boolean matchCase) throws SQLException;
}
