package guinfe.main;

import guinfe.connection.Connect;
import guinfe.presenter.ConnectPresenter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Efetua inicializações da aplicação.
 */
public class Inicializador {


  /**
   * Inicializa a aplicação.
   * <p>
   *   Conecta com o banco de dados(notifica caso seja disparada alguma exceção), verifica
   *   a existência da tabela 'schema_nfe'(caso não exista será criada) e executa script
   *   para inserir os registro nesta tabela caso ela seja criada.
   * </p>
   *
   * <p>
   *   Se as exceções foram disparadas será exibida uma notificação.
   * </p>
   */
  public void init() {
    final ConnectPresenter iConnectPresenter = new Connect();
    final Connection connection = iConnectPresenter.conectarBancoDeDados();

    try {
      final boolean tabelaExiste = iConnectPresenter.tabelaSchemaNfeExiste(connection);
      if (!tabelaExiste) {
        final ThreadDB threadDB = new ThreadDB();
        threadDB.start();

        iConnectPresenter.criarTabelaSchemaNfe(connection);
        iConnectPresenter.executarScript(connection);

        threadDB.stopThread();
      }

    } catch (SQLException e) {
      System.err.println(">>> Não foi possível conectar no banco de dados/executar script:\n" + e.getMessage());

    } catch (IOException e) {
      System.err.println(">> Ocorreu um problema inesperado.\n" + e.getMessage());

    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
