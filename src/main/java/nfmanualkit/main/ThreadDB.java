package nfmanualkit.main;

/**
 * Classe responsável por exibir notificação em quanto o banco de dados é configurado.
 */
public class ThreadDB extends Thread {

  private volatile boolean dbConfigurado = false;

  private volatile int contador = 0;

  @Override
  public void run() {
    String aux = "\rConfigurando o banco de dados. Aguarde ";

    while(!dbConfigurado) {
      switch (contador) {
        case 0:
          System.out.print(aux + "-");
          break;

        case 1:
          System.out.print(aux + "\\");
          break;

        case 2:
          System.out.print(aux + "|");
          break;

        case 3:
          System.out.print(aux + "/");
          break;

        case 4:
          System.out.print(aux + "-");
          break;

        default:
          break;
      }

      try {
        Thread.sleep(400);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      if (contador == 4) {
        contador = 0;
      }

      contador++;
    }
  }

  public void stopThread() {
    System.out.println(" CONCLUÍDO");
    System.out.println();
    dbConfigurado = true;
  }
}
