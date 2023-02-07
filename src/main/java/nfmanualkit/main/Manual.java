package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.connection.Dao;
import nfmanualkit.view.ManualView;

import java.sql.SQLException;
import java.util.List;

public class Manual {

  private DaoPresenter daoPresenter;

  private ManualView view;

  public Manual() {
    this.daoPresenter = new Dao();
  }

  public void setView(ManualView view) {
    this.view = view;
  }

  // TODO: 06/02/2023 inserir doc
  public void listar(String filtro) throws SQLException {
    final List<SchemaNfe> lista = daoPresenter.listar(filtro);
    view.exibir(lista);
  }
}
