package nfmanualkit.main;

import nfmanualkit.connection.Connect;
import nfmanualkit.presenter.ConnectPresenter;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

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

      System.out.println("Encerrando...");
      System.exit(0);
    });
  }
}

class ThreadDB extends Thread {
  private volatile boolean dbConfigurado = false;

  private volatile int contador = 0;

  @Override
  public void run() {
    String aux = "Configurando o banco de dados. Aguarde";
    System.out.print(aux);

    while(!dbConfigurado) {
      System.out.print(".");

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      contador++;
      if (contador == 3) {
        contador = 0;
        System.out.print("\r" + aux);
      }
    }
  }

  public void stopThread() {
    System.out.print(" CONCLUÍDO");
    System.out.println();
    dbConfigurado = false;
  }
}
