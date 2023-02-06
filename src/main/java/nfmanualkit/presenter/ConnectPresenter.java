package nfmanualkit.presenter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectPresenter {

  Connection conectarBancoDeDados();

  boolean tabelaSchemaNfeExiste(Connection connection) throws SQLException;

  void criarTabelaSchemaNfe(Connection connection) throws SQLException;

  void executarScript(Connection connection) throws SQLException, IOException;
}
