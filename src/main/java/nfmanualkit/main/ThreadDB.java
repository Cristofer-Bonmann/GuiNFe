package nfmanualkit.main;

/**
 * Classe responsável por exibir notificação em quanto o banco de dados é configurado.
 */
public class ThreadDB extends Thread {

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
