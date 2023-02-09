package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.connection.Dao;
import nfmanualkit.view.ManualView;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe responsável por acessar e enviar dados do {@link Dao} para o {@link ManualView}.
 */
public class Manual {

  private DaoPresenter daoPresenter;

  private ManualView view;

  /**
   * Contrutor da classe. Instância {@link DaoPresenter}.
   */
  public Manual() {
    this.daoPresenter = new Dao();
  }

  /**
   * Atribuí o {@link ManualView}.
   * @param view objeto {@link ManualView}.
   */
  public void setView(ManualView view) {
    this.view = view;
  }


  /**
   * Exibe a lista de {@link SchemaNfe} na View.
   * @return lista de objetos {@link SchemaNfe}.
   *
   * @throws SQLException
   */
  public List<SchemaNfe> listarTodos() throws SQLException {
    final List<SchemaNfe> lista = daoPresenter.listar();
    view.exibir(lista);
    return lista;
  }

  /**
   * Exibe a lista de {@link SchemaNfe} filtrada pelo termo(parâmetro filtro) na View.
   * @param filtro termo que filtrará o resultado.
   *
   * @throws SQLException
   */
  public void listarPorFiltro(String filtro) throws SQLException {
    final List<SchemaNfe> lista = daoPresenter.listar(filtro);
    view.exibir(lista);
  }
}
