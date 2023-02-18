package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.connection.Dao;
import nfmanualkit.view.ManualView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

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
  // TODO: 16/02/2023 atualização para adicionar parâmetro 'matchCase'.
  public void listarPorFiltro(String filtro) throws SQLException {
    final List<SchemaNfe> lista;

    if (!filtro.trim().equals("")) {
      final EFiltro eFiltro = view.getSelectedEFiltro();
      final boolean matchCase = view.isMatchCase();
      final boolean ocorrenciaPalavra = view.isOcorrenciaPalavra();
      lista = daoPresenter.listar(eFiltro, filtro, matchCase, ocorrenciaPalavra);
    } else {
      lista = daoPresenter.listar();
    }

    view.exibir(lista);
  }

  // TODO: 09/02/2023 inserir doc
  public Object[][] getMatrizIdGrupo(List<SchemaNfe> lista) {
    final Object[][] data = new Object[lista.size()][1];

    IntStream.range(0, lista.size()).forEach(index -> {
      final SchemaNfe schemaNfe = lista.get(index);
      final String idGrupo = schemaNfe.getIdGrupo();

      data[index][0] = idGrupo;
    });

    return data;
  }

  // TODO: 09/02/2023 inserir doc
  public Object[] getVetorColunaIdGrupo() {
    final Object[] columnNames = new Object[1];
    columnNames[0] = "";
    return columnNames;
  }

  // TODO: 10/02/2023 inserir doc
  public void selecionarFiltro(String nomeColuna) {
    final EFiltro eFiltro = EFiltro.getPorRotulo(nomeColuna);
    view.setSelectedEFiltro(eFiltro);
  }
}
