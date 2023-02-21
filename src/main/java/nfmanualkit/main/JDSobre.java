package nfmanualkit.main;

import nfmanualkit.util.Recursos;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JDSobre extends JDialog {

  public JDSobre(JFrame jFrame) {
    super(jFrame, true);

    initComponents();
    inits();
  }

  private void inits() {
    final InputStream isSobre = getClass().getClassLoader().getResourceAsStream("sobre.html");
    try {
      String htmlText = IOUtils.toString(isSobre, StandardCharsets.UTF_8);
      jlSobre.setText(htmlText);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private JLabel jlSobre;

  private void initComponents() {
    final Dimension dimension = new Dimension(600, 500);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(dimension);
    setPreferredSize(dimension);
    setLocationRelativeTo(null);

    setLayout(new BorderLayout());

    final JPanel jpSobre = new JPanel(new FlowLayout());
    jlSobre = new JLabel("");
    jpSobre.add(jlSobre);

    add(jpSobre, BorderLayout.CENTER);
  }
}
