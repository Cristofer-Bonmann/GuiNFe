package guinfe.presenter;

import guinfe.entity.SchemaNfe;
import guinfe.enumeracao.EFiltro;

import java.sql.SQLException;
import java.util.List;

public interface DaoPresenter {

  List<SchemaNfe> listar() throws SQLException;

  List<SchemaNfe> listar(EFiltro eFiltro, String filtro, boolean matchCase, boolean ocorrenciaPalavra) throws SQLException;
}
