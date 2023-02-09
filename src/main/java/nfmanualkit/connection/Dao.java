package nfmanualkit.connection;

import nfmanualkit.entity.SchemaNfe;
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
   * Lista todos os resultados da tabela 'schema_nfe' filtrando por: idGrupo.
   * @param filtro termo que filtrará os resultados.
   * @return lista de objeto {@link SchemaNfe};
   *
   * @throws SQLException
   */
  @Override
  public List<SchemaNfe> listar(String filtro) throws SQLException {
    final String query = "SELECT * FROM schema_nfe WHERE idGrupo LIKE ? ORDER BY id";

    final PreparedStatement preparedStatement = getConnection().prepareStatement(query);
    preparedStatement.setString(1, "%" + filtro + "%");

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
