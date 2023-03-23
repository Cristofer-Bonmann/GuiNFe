package guinfe.main;

import guinfe.util.Recursos;
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
    initEvents();
  }

  private void initEvents() {

    jbOk.addActionListener(actionEvent -> {
      dispose();
    });
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

  private JButton jbOk;

  private void initComponents() {
    final Dimension dimension = new Dimension(600, 250);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(dimension);
    setPreferredSize(dimension);
    setLocationRelativeTo(null);
    setResizable(false);
    setTitle(Recursos.get("jdsobre_titulo"));

    setLayout(new BorderLayout());

    final JPanel jpSobre = new JPanel(new FlowLayout());
    jpSobre.setBackground(Color.WHITE);
    jlSobre = new JLabel("");
    jlSobre.setBackground(Color.WHITE);
    jpSobre.add(jlSobre);

    final JPanel jpOk = new JPanel();
    jpOk.setLayout(new FlowLayout());

    jbOk = new JButton(Recursos.get("jdsobre_ok"));
    jpOk.setBackground(Color.WHITE);
    jbOk.setPreferredSize(new Dimension(100, 27));
    jpOk.add(jbOk);

    add(jpSobre, BorderLayout.CENTER);
    add(jpOk, BorderLayout.SOUTH);
  }
}
