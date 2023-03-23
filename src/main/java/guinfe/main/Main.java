package guinfe.main;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

      final Inicializador inicializador = new Inicializador();
      inicializador.init();

      final JFGuiNFe jfGuiNFe = new JFGuiNFe();
      jfGuiNFe.setVisible(true);
    });
  }
}

