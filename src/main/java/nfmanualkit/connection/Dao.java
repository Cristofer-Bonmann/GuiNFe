package nfmanualkit.connection;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.presenter.ConnectPresenter;
import nfmanualkit.presenter.DaoPresenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO: 06/02/2023 inserir doc
public class Dao implements DaoPresenter {

  private ConnectPresenter connectPresenter;

  private Connection connection;

  public Dao() {
    connectPresenter = new Connect();
    connection = connectPresenter.conectarBancoDeDados();
  }

  // TODO: 08/02/2023 inserir doc
  private Connection getConnection() throws SQLException {
    if (this.connection == null || this.connection.isClosed())
      this.connection = connectPresenter.conectarBancoDeDados();

    return this.connection;
  }

  // TODO: 08/02/2023 inserir doc
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

  // TODO: 06/02/2023 inserir doc
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
