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

  private boolean conectado = false;

  public Dao() {
    connectPresenter = new Connect();
    connection = connectPresenter.conectarBancoDeDados();
    try {
      conectado = !connection.isClosed();
    } catch (SQLException e) {
      System.err.println(">>> Não foi possível verificar a conexão do banco de dados:\n" + e.getMessage());
    }
  }

  // TODO: 06/02/2023 inserir doc
  @Override
  public List<SchemaNfe> listar(String filtro) throws SQLException {
    final String query = "SELECT * FROM schema_nfe WHERE idGrupo LIKE %?%";

    final PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, filtro);

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

    return lista;
  }
}
