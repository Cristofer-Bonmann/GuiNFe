package nfmanualkit.main;

import javax.swing.*;
import java.awt.*;

public class JFSchemaNfe extends JFrame {

  public JFSchemaNfe() {
    initComponents();
    inits();
    initEvents();
  }

  private void inits() {
    jbDepreciar.setEnabled(false);
  }

  private void initEvents() {

    jtSchemaNfe.getSelectionModel().addListSelectionListener(e -> {
      jbDepreciar.setEnabled(jtSchemaNfe.getSelectedRow() != -1);
    });
  }

  private JButton jbPesquisar;
  private JButton jbDepreciar;
  private JTextField jtfFiltro;
  private JScrollPane jspSchemaNfe;
  private JTable jtSchemaNfe;

  public void initComponents() {
    setTitle("NFSchemaKit");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    jbPesquisar = new JButton(new ImageIcon(getClass().getResource("/lupa.png")));

    jtfFiltro = new JTextField();
    jtfFiltro.setPreferredSize(new Dimension(300, 25));

    jtSchemaNfe = new JTable();
    jspSchemaNfe = new JScrollPane(jtSchemaNfe);

    jbDepreciar = new JButton("Depreciar");

    final JPanel jpTop = new JPanel(new BorderLayout());
    final JPanel jpTopII = new JPanel();
    final JPanel jpTopIII = new JPanel();

    jpTopII.setLayout(new FlowLayout());
    jpTopII.add(jtfFiltro);
    jpTopII.add(jbPesquisar);

    jpTopIII.add(jbDepreciar);

    jpTop.add(jpTopIII, BorderLayout.WEST);
    jpTop.add(jpTopII, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane(jspSchemaNfe);

    add(jpTop, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }
}
