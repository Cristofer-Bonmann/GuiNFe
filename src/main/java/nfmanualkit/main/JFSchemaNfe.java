package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.util.*;
import nfmanualkit.view.ManualView;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class JFSchemaNfe extends JFrame implements ManualView {

  private Manual manual;

  private TableModel<SchemaNfe> tmSchemaNfe;

  private TableCellRendererAltura tcrAltura;

  public JFSchemaNfe() {
    initComponents();
    inits();
    initEvents();
  }

  @Override
  public void exibir(List<SchemaNfe> lista) {
    tmSchemaNfe.novaLista(lista);
    JTableAjusteColunas.ajustarColunas(jtSchemaNfe);
    tcrAltura.adjustRowHeights();
  }

  private void inits() {
    manual = new Manual();
    manual.setView(this);

    tmSchemaNfe = new TableModel(SchemaNfe.class);
    jtSchemaNfe.setModel(tmSchemaNfe);
    tcrAltura = new TableCellRendererAltura(jtSchemaNfe);

    final TableCellRendererHtml tcrHtml = new TableCellRendererHtml();
    final TableColumnModel columnModel = jtSchemaNfe.getColumnModel();
    for (int i = 0; i < columnModel.getColumnCount(); i++) {
      columnModel.getColumn(i).setCellRenderer(tcrHtml);
    }
  }

  private void initEvents() {

    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        super.windowOpened(e);

        try {
          manual.listarTodos();
        } catch (SQLException ex) {
          // TODO: 08/02/2023 exibir notificação.
          throw new RuntimeException(ex);
        }
      }
    });

    jtfFiltro.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          try {
            manual.listarPorFiltro(jtfFiltro.getText());
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

  private JTable jtSchemaNfeLateral;

  public void initComponents() {
    setTitle("NFSchemaKit");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    jtfFiltro = new JTextField();
    jtfFiltro.setPreferredSize(new Dimension(300, 25));

    jtSchemaNfe = new JTable();
    jtSchemaNfe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jspSchemaNfe = new JScrollPane(jtSchemaNfe);
    JTableAjusteColunas.alinharHeader(jtSchemaNfe);

    jtSchemaNfeLateral = new JTable();
    jtSchemaNfeLateral.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jtSchemaNfeLateral.setPreferredScrollableViewportSize(new Dimension(50, 0));
    jtSchemaNfeLateral.setDefaultRenderer(Object.class, new RowHeaderRenderer(jtSchemaNfe));
    jspSchemaNfe.setRowHeaderView(jtSchemaNfeLateral);

    final JPanel jpTop = new JPanel(new BorderLayout());
    final JPanel jpTopII = new JPanel();

    jpTopII.setLayout(new FlowLayout());
    jpTopII.add(jtfFiltro);

    jpTop.add(jpTopII, BorderLayout.CENTER);

    add(jpTop, BorderLayout.NORTH);
    add(jspSchemaNfe, BorderLayout.CENTER);
  }
}
