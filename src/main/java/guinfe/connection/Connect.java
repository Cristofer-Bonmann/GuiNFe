package guinfe.connection;

import guinfe.presenter.ConnectPresenter;

import java.io.*;
import java.sql.*;

public class Connect implements ConnectPresenter {

  /**
   * Estabelece conexão com o banco de dados por uma determinada URL(parâmetro url).<br>
   * Ex.: jdbc:sqlite:db/database-name.db
   *
   * @param url de conexão JDBC.
   * @return objeto Connection.
   *
   * @throws SQLException
   */
  protected Connection conectar(String url) throws SQLException {
    return DriverManager.getConnection(url);
  }

  /**
   * Conecta com o banco de dados.
   *
   * @return objeto Connection da conexão atual.
   */
  @Override
  public Connection conectarBancoDeDados() {
    Connection connection = null;

    final String dbDiretorio = System.getProperty("user.dir") + File.separator + "db";
    final File fileDBDiretorio = new File(dbDiretorio);
    if (!fileDBDiretorio.exists()) {
      fileDBDiretorio.mkdir();
    }

    final String url = String.format("jdbc:sqlite:%s/database.db", dbDiretorio);

    try {
      connection = conectar(url);
    } catch(SQLException sqle){
      System.err.println(">>> Não foi possível conectar com o banco de dados 'database.db':\n" +
              sqle.getMessage());
    }

    return connection;
  }

  /**
   * Verifica se a tabela 'schema_nfe' existe no banco de dados.
   *
   * @param connection conexão atual do banco de dados.
   * @return true caso a tabela exista, false caso contrário.
   *
   * @throws SQLException
   */
  @Override
  public boolean tabelaSchemaNfeExiste(Connection connection) throws SQLException {
    final DatabaseMetaData metaData = connection.getMetaData();
    final ResultSet resultSet = metaData.getTables(null, null, "schema_nfe", null);
    return resultSet.next();
  }

  /**
   * Criar table 'schema_nfe' no banco de dados.
   *
   * @param connection conexão com o banco de dados.
   *
   * @throws SQLException
   */
  @Override
  public void criarTabelaSchemaNfe(Connection connection) throws SQLException {
    final String query = "CREATE TABLE schema_nfe("
    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
    + "idGrupo CHARACTER VARYING NOT NULL DEFAULT '',"
    + "campo CHARACTER VARYING NOT NULL DEFAULT '',"
    + "descricao CHARACTER VARYING NOT NULL DEFAULT '',"
    + "elemento CHARACTER VARYING NOT NULL DEFAULT '',"
    + "pai CHARACTER VARYING NOT NULL DEFAULT '',"
    + "tipo CHARACTER VARYING NOT NULL DEFAULT '',"
    + "ocorrencia CHARACTER VARYING NOT NULL DEFAULT '',"
    + "tamanho CHARACTER VARYING NOT NULL DEFAULT '',"
    + "observacao CHARACTER VARYING NOT NULL DEFAULT ''"
    + ")";

    final Statement statement = connection.createStatement();
    statement.executeUpdate(query);
  }

  /**
   * Executa o script que adiciona os registros na tabela SchemaNfe.
   * @param connection parâmetro para conexão com o banco de dados.
   *
   * @throws SQLException
   * @throws IOException
   */
  @Override
  public void executarScript(Connection connection) throws SQLException, IOException {
    final Statement statement = connection.createStatement();

    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("script/script_schema_nfe.sql");
    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    String scriptConteudo = "";
    String linha;
    while ((linha = reader.readLine()) != null) {
      scriptConteudo += linha;
    }
    reader.close();

    statement.executeUpdate(scriptConteudo);
  }
}
