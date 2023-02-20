package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.util.*;
import nfmanualkit.view.ManualView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import static javax.swing.JOptionPane.showOptionDialog;

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
        ajustarAlturaLateral(lista);
    }

    @Override
    public void setSelectedEFiltro(EFiltro eFiltro) {
        jcbFiltro.setSelectedItem(eFiltro);
    }

    @Override
    public EFiltro getSelectedEFiltro() {
        return (EFiltro) jcbFiltro.getSelectedItem();
    }

    @Override
    public boolean isMatchCase() {
        return jtbMatchCase.isSelected();
    }

    @Override
    public boolean isOcorrenciaPalavra() {
        return jtbOcorrenciaPalavra.isSelected();
    }

    @Override
    public String getFiltro() {
        return jtfFiltro.getText();
    }

    @Override
    public void setMatchCaseSelected(boolean matchCase) {
        jtbMatchCase.setSelected(matchCase);
    }

    @Override
    public void setOcorrenciaLetraSelected(boolean ocorrenciaLetra) {
        jtbOcorrenciaPalavra.setSelected(ocorrenciaLetra);
    }

    /**
     * <p>
     * Adiciona um {@link DefaultTableModel} no {@link JTable} jtSchemaNfeLateral. Este TableModel possue apenas uma
     * coluna com linhas populadas com o 'idGrupo' da lista passada por parâmetro(lista). Também aplica a altura da linha
     * da jtSchemanfe na jtSchemaNfeLateral correspondente.
     * </p>
     *
     * @param lista lista de objetos SchemaNfe.
     */
    private void ajustarAlturaLateral(List<SchemaNfe> lista) {
        final Object[][] data = manual.getMatrizIdGrupo(lista);
        final Object[] columnNames = manual.getVetorColunaIdGrupo();
        jtSchemaNfeLateral.setModel(new DefaultTableModel(data, columnNames));

        IntStream.range(0, lista.size()).forEachOrdered(index -> {
            final int rowHeight = jtSchemaNfe.getRowHeight(index);
            jtSchemaNfeLateral.setRowHeight(index, rowHeight);
        });
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

        ToolTipManager.sharedInstance().setInitialDelay(0);
        jtbMatchCase.setToolTipText(Recursos.get("jtbmatchcase_tolltiptext"));
        jtbOcorrenciaPalavra.setToolTipText(Recursos.get("jtbocorrenciacaracteres_tolltiptext"));
    }

    private void fechar() {
        try {
            manual.salvarConfiguracoes();
        } catch (IOException e) {
            // TODO: 20/02/2023 exibir notificação.
            throw new RuntimeException(e);
        }

        final Object[] opcoes = new Object[] {"Sim", "Não"};
        final int i = showOptionDialog(this, "Deseja fechar a aplicação?", "Confirme",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (i == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void initEvents() {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                fechar();
                return true;
            }
            return false;
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                try {
                    manual.carregarConfiguracoes();
                    manual.listarTodos();

                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                } catch (SQLException ex) {
                    // TODO: 08/02/2023 exibir notificação.
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                fechar();
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

        jtfFiltro.getDocument().addDocumentListener(new DocumentListenerAdapter() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    manual.listarTodos();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // TODO: 11/02/2023 exibir notificação
                }
            }
        });

        jtSchemaNfe.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                final int colunaPoint = jtSchemaNfe.getTableHeader().columnAtPoint(e.getPoint());
                final String headerValue = jtSchemaNfe.getColumnModel().getColumn(colunaPoint).getHeaderValue().toString();
                manual.selecionarFiltro(headerValue);
            }
        });

        jtbMatchCase.addActionListener(actionEvent -> {
            if (!jtfFiltro.getText().trim().equals("")) {
                try {
                    manual.listarPorFiltro(jtfFiltro.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                    // TODO: 16/02/2023 exibir notificação.
                }
            }
        });

        jtbOcorrenciaPalavra.addActionListener(actionEvent -> {
            if (!jtfFiltro.getText().trim().equals("")) {
                try {
                    manual.listarPorFiltro(jtfFiltro.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                    // TODO: 16/02/2023 exibir notificação.
                }
            }
        });
    }

    private JComboBox<EFiltro> jcbFiltro;
    private JTextField jtfFiltro;
    private JToggleButton jtbMatchCase;
    private JToggleButton jtbOcorrenciaPalavra;
    private JScrollPane jspSchemaNfe;
    private JTable jtSchemaNfe;
    private JTable jtSchemaNfeLateral;
    private JLabel jlAtualizacaoTecnica;
    private JLabel jlVersao;

    public void initComponents() {
        setTitle("NFSchemaKit");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        final JPanel jpPrincipal = new JPanel(new BorderLayout());
        final JPanel jpCentro = new JPanel(new BorderLayout());
        final JPanel jpTop = new JPanel(new BorderLayout());
        final JPanel jpTopII = new JPanel();
        final JPanel jpSouth = new JPanel(new BorderLayout());

        jcbFiltro = new JComboBox<>(EFiltro.values());
        jcbFiltro.setPreferredSize(new Dimension(150, 25));

        jtfFiltro = new JTextField();
        jtfFiltro.setPreferredSize(new Dimension(400, 25));

        jtbMatchCase = new JToggleButton("Mc");
        jtbMatchCase.setPreferredSize(new Dimension(60, 25));

        jtbOcorrenciaPalavra = new JToggleButton("P");
        jtbOcorrenciaPalavra.setPreferredSize(new Dimension(60, 25));

        jtSchemaNfe = new JTable();
        jtSchemaNfe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jspSchemaNfe = new JScrollPane(jtSchemaNfe);
        JTableAjusteColunas.alinharHeader(jtSchemaNfe);

        final Font font = new Font("Dialog", Font.BOLD, 17);
        jlAtualizacaoTecnica = new JLabel(Recursos.get("jlatualizacaotecnica", Sistema.ATUALIZACAO_TECNICA));
        jlAtualizacaoTecnica.setFont(font);
        jlVersao = new JLabel(Recursos.get("jlversao"));
        jlVersao.setFont(font);

        jtSchemaNfeLateral = new JTable();
        jtSchemaNfeLateral.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jtSchemaNfeLateral.setPreferredScrollableViewportSize(new Dimension(75, 0));
        jtSchemaNfeLateral.setDefaultRenderer(Object.class, new TableCellRendererHeader(jtSchemaNfe));
        jspSchemaNfe.setRowHeaderView(jtSchemaNfeLateral);

        jpTopII.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpTopII.add(jcbFiltro);
        jpTopII.add(jtfFiltro);
        jpTopII.add(jtbMatchCase);
        jpTopII.add(jtbOcorrenciaPalavra);

        jpSouth.add(jlAtualizacaoTecnica, BorderLayout.WEST);
        jpSouth.add(jlVersao, BorderLayout.EAST);

        jpTop.add(jpTopII, BorderLayout.CENTER);
        jpCentro.add(jpTop, BorderLayout.NORTH);
        jpCentro.add(jspSchemaNfe, BorderLayout.CENTER);
        jpCentro.add(jpSouth, BorderLayout.SOUTH);
        jpPrincipal.add(jpCentro);

        add(jpPrincipal, BorderLayout.CENTER);
    }
}
