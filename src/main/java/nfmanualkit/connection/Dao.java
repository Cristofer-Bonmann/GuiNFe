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

  // TODO: 09/02/2023 inserir doc
  protected String montarQuery(EFiltro eFiltro, boolean matchCase, boolean ocorrenciaPalavra) {
    String query;

    final String operador = ocorrenciaPalavra ? " LIKE " : " = ";

    switch (eFiltro) {
      case TODOS:
        query = "SELECT * FROM schema_nfe WHERE " +
                "idGrupo" +  operador + "? OR \n" +
                "campo" +  operador + "? OR \n" +
                "descricao" +  operador + "? OR \n" +
                "elemento" +  operador + "? OR \n" +
                "pai" +  operador + "? OR \n" +
                "tipo" +  operador + "? OR \n" +
                "ocorrencia" +  operador + "? OR \n" +
                "tamanho" +  operador + "? OR \n" +
                "observacao" +  operador + "? " +
                "ORDER BY id";
        break;

      case IDGRUPO:
        query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro() + operador + "? OR pai" + operador + "? ORDER BY id";
        break;

      case CAMPO:
        query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro() + operador + "? OR pai" + operador +
        "(SELECT idGrupo FROM schema_nfe WHERE campo" + operador + "? ORDER BY id LIMIT 1) ORDER BY id";
        break;

      default:
      query = "SELECT * FROM schema_nfe WHERE " + eFiltro.getFiltro() + operador + "? ORDER BY id";
    }

    return query;
  }

  /**
   * Lista todos os resultados da tabela 'schema_nfe' filtrando por: idGrupo.
   *
   * @param eFiltro filtro da consulta.
   * @param filtro termo que filtrará os resultados.
   * @return lista de objeto {@link SchemaNfe};
   *
   * @throws SQLException
   */
  // TODO: 16/02/2023 atualização para adicionar o parâmetro 'matchCase' e 'ocorrenciaPalavra'.
  @Override
  public List<SchemaNfe> listar(EFiltro eFiltro, String filtro, boolean matchCase, boolean ocorrenciaPalavra) throws SQLException {
    final String query = montarQuery(eFiltro, matchCase, ocorrenciaPalavra);

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
