package nfmanualkit.presenter;

import nfmanualkit.entity.SchemaNfe;

import java.sql.SQLException;
import java.util.List;

public interface DaoPresenter {

  List<SchemaNfe> listar() throws SQLException;

  List<SchemaNfe> listar(String filtro) throws SQLException;
}
