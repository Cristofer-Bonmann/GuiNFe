package nfmanualkit.connection;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.ConnectPresenter;
import nfmanualkit.presenter.DaoPresenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por acessar, persistir ou remover dados do banco de dados.
 */
public class Dao implements DaoPresenter {

  private ConnectPresenter connectPresenter;

  private Connection connection;

  /**
   * Construtor da classe. Instância a interface {@link Connect} e conecta com o banco de dados.
   */
  public Dao() {
    connectPresenter = new Connect();
    connection = connectPresenter.conectarBancoDeDados();
  }

  /**
   * Se a conexão atual com o banco for igual a 'null' ou estiver fechada, uma nova conexão será aberta.
   * @return retorna uma conexão com o banco de dados.
   *
   * @throws SQLException
   */
  private Connection getConnection() throws SQLException {
    if (this.connection == null || this.connection.isClosed())
      this.connection = connectPresenter.conectarBancoDeDados();

    return this.connection;
  }

  /**
   * Lista todos os resultados da tabela 'schema_nfe'.
   * @return lista de objeto {@link SchemaNfe}.
   *
   * @throws SQLException
   */
  @Override
  public List<SchemaNfe> listar() throws SQLException {
    final String query = "SELECT * FROM schema_nfe ORDER BY id";

    final PreparedStatement preparedStatement = getConnection().prepareStatement(query);
    final ResultSet resultSet = preparedStatement.executeQuery();

    final List<SchemaNfe> lista = new ArrayList<>();

    while(resultSet.next()) {
      final int id = resultSet.getInt("id");
      final String idGrupo = resultSet.getString("idGrupo");
      final String campo = resultSet.getString("campo");
      final String descricao = resultSet.getString("descricao");
      final String elemento = resultSet.getString("elemento");
      final String pai = resultSet.getString("pai");
      final String tipo = resultSet.getString("tipo");
      final String ocorrencia = resultSet.getString("ocorrencia");
      final String tamanho = resultSet.getString("tamanho");
      final String observacao = resultSet.getString("observacao");

      final SchemaNfe schemaNfe = SchemaNfe.builder()
              .id(id)
              .idGrupo(idGrupo)
              .campo(campo)
              .descricao(descricao)
              .elemento(elemento)
              .pai(pai)
              .tipo(tipo)
              .ocorrencia(ocorrencia)
              .tamanho(tamanho)
              .observacao(observacao)
              .build();

      lista.add(schemaNfe);
    }

    resultSet.close();
    preparedStatement.close();
    connection.close();

    return lista;
  }

  /**
   * Monta query para consulta no banco de dados.
   *
   * @param eFiltro filtro que será usando para a consulta.(veja {@link EFiltro}).
   * @param matchCase true = query irá considerará letras maiúsculas.
   * @param ocorrenciaPalavra true = query utilizará o operador 'like' na comparação do filtro. Se false, utilizará o
   *                          operador '='.
   *
   * @return String que representa a query gerada.
   */
  protected String montarQuery(EFiltro eFiltro, boolean matchCase, boolean ocorrenciaPalavra) {
    String query;

    final String operador = ocorrenciaPalavra ? " LIKE " : " = ";

    switch (eFiltro) {
      case TODOS:
        query = "SELECT * FROM schema_nfe WHERE " +
                EFiltro.IDGRUPO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.CAMPO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.DESCRICAO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.ELEMENTO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.PAI.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.TIPO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.OCORRENCIA.getFiltro(matchCase) +  operador + "? OR \n" +
                EFiltro.TAMANHO.getFiltro(matchCase) + operador + "? OR \n" +
                EFiltro.OBSERVACAO.getFiltro(matchCase) + operador + "? " +
                "ORDER BY id";
        break;

      case IDGRUPO:
        query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro(matchCase) + operador + "? OR "
                + EFiltro.PAI.getFiltro(matchCase) + operador + "? ORDER BY id";
        break;

      case CAMPO:
        query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro(matchCase) + operador + "? OR "
                + EFiltro.PAI.getFiltro(matchCase) + operador +
        "(SELECT "+ (matchCase ? "idGrupo" : "lower(idGrupo)") + " FROM schema_nfe WHERE " + EFiltro.CAMPO.getFiltro(matchCase) + operador + "? " +
                "ORDER BY id LIMIT 1) ORDER BY id";
        break;

      default:
      query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro(matchCase) + operador + "? ORDER BY id";
    }
    return query;
  }

  /**
   * Define a modalidade do operador de comparação que será usado no filtro(parâmetro).
   * @param filtro filtro que será formatado.
   * @param ocorrenciaPalavra defini a modalidade comparação.
   *
   * @return true = "'%termo do filtro%'", false = 'termo do filtro'.
   */
  protected String montarFiltroComOcorrenciaPalavra(String filtro, boolean ocorrenciaPalavra) {
    return ocorrenciaPalavra ? "%" + filtro + "%" : filtro;
  }

  /**
   * Define se o filtro(parâmetro) será formatado para maiúsculo ou minúsculo.
   * @param filtro filtro que será formatado.
   * @param matchCase define se o termo do filtro será formatado para maiúsculo ou minúsculo.
   *
   * @return true = maiúsculo, false = minúsculo.
   */
  protected String montarFiltroMatchCase(String filtro, boolean matchCase) {
    return matchCase ? filtro : filtro.toLowerCase();
  }

  /**
   * Lista todos os registros {@link SchemaNfe} do banco de dados filtrando por: tipo do filtro(eFiltro),
   * termo do filtro(filtro), diferenciação de letras(matchCase: maiúscula ou minúscula) e se a busca será feita com
   * comparação '=' ou 'like'.
   *
   * @param eFiltro filtro da consulta.
   * @param filtro termo que filtrará os resultados.
   * @param matchCase true = maiúscula, false = minúscula.
   * @param ocorrenciaPalavra true = 'like', false = '='.
   *
   * @return lista de objeto {@link SchemaNfe};
   *
   * @throws SQLException
   */
  @Override
  public List<SchemaNfe> listar(EFiltro eFiltro, String filtro, boolean matchCase, boolean ocorrenciaPalavra) throws SQLException {
    final String query = montarQuery(eFiltro, matchCase, ocorrenciaPalavra);
    filtro = montarFiltroComOcorrenciaPalavra(filtro, ocorrenciaPalavra);
    filtro = montarFiltroMatchCase(filtro, matchCase);

    final PreparedStatement preparedStatement = getConnection().prepareStatement(query);

    switch (eFiltro) {
      case TODOS:
        preparedStatement.setString(1, filtro);
        preparedStatement.setString(2, filtro);
        preparedStatement.setString(3, filtro);
        preparedStatement.setString(4, filtro);
        preparedStatement.setString(5, filtro);
        preparedStatement.setString(6, filtro);
        preparedStatement.setString(7, filtro);
        preparedStatement.setString(8, filtro);
        preparedStatement.setString(9, filtro);
        break;

      case IDGRUPO:
      case CAMPO:
        preparedStatement.setString(1, filtro);
        preparedStatement.setString(2, filtro);
        break;

      default:
        preparedStatement.setString(1, filtro);
        break;
    }

    final ResultSet resultSet = preparedStatement.executeQuery();

    final List<SchemaNfe> lista = new ArrayList<>();

    while(resultSet.next()) {
      final int id = resultSet.getInt("id");
      final String idGrupo = resultSet.getString("idGrupo");
      final String campo = resultSet.getString("campo");
      final String descricao = resultSet.getString("descricao");
      final String elemento = resultSet.getString("elemento");
      final String pai = resultSet.getString("pai");
      final String tipo = resultSet.getString("tipo");
      final String ocorrencia = resultSet.getString("ocorrencia");
      final String tamanho = resultSet.getString("tamanho");
      final String observacao = resultSet.getString("observacao");

      final SchemaNfe schemaNfe = SchemaNfe.builder()
              .id(id)
              .idGrupo(idGrupo)
              .campo(campo)
              .descricao(descricao)
              .elemento(elemento)
              .pai(pai)
              .tipo(tipo)
              .ocorrencia(ocorrencia)
              .tamanho(tamanho)
              .observacao(observacao)
              .build();

      lista.add(schemaNfe);
    }

    resultSet.close();
    preparedStatement.close();
    connection.close();

    return lista;
  }
}
