package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.util.JTableAjusteColunas;
import nfmanualkit.util.TableModel;
import nfmanualkit.view.ManualView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

public class JFSchemaNfe extends JFrame implements ManualView {

  private Manual manual;

  private TableModel<SchemaNfe> tmSchemaNfe;

  public JFSchemaNfe() {
    initComponents();
    inits();
    initEvents();
  }

  @Override
  public void exibir(List<SchemaNfe> lista) {
    tmSchemaNfe.novaLista(lista);
    JTableAjusteColunas.ajustarColunas(jtSchemaNfe);
  }

  private void inits() {
    manual = new Manual();
    manual.setView(this);

    tmSchemaNfe = new TableModel(SchemaNfe.class);
    jtSchemaNfe.setModel(tmSchemaNfe);

    jtSchemaNfe.setRowHeight(25);
  }

  private void initEvents() {

    jtfFiltro.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          try {
            manual.listar(jtfFiltro.getText());
          } catch (SQLException ex) {
            // TODO: 07/02/2023 exibir notificação.
            ex.printStackTrace();
          }
        }
      }
    });
  }

  private JTextField jtfFiltro;
  private JScrollPane jspSchemaNfe;
  private JTable jtSchemaNfe;

  public void initComponents() {
    setTitle("NFSchemaKit");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    jtfFiltro = new JTextField();
    jtfFiltro.setPreferredSize(new Dimension(300, 25));

    jtSchemaNfe = new JTable();
    jspSchemaNfe = new JScrollPane(jtSchemaNfe);
    JTableAjusteColunas.alinharHeader(jtSchemaNfe);

    final JPanel jpTop = new JPanel(new BorderLayout());
    final JPanel jpTopII = new JPanel();

    jpTopII.setLayout(new FlowLayout());
    jpTopII.add(jtfFiltro);

    jpTop.add(jpTopII, BorderLayout.CENTER);

    add(jpTop, BorderLayout.NORTH);
    add(jspSchemaNfe, BorderLayout.CENTER);
  }
}
