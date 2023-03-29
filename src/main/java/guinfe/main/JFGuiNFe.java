package guinfe.main;

import guinfe.entity.SchemaNfe;
import guinfe.enumeracao.EFiltro;
import guinfe.util.*;
import guinfe.view.ManualView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.IntStream;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;

public class JFGuiNFe extends JFrame implements ManualView {

    private Manual manual;
    private TableModel<SchemaNfe> tmSchemaNfe;
    private TableCellRendererAltura tcrAltura;

    public JFGuiNFe() {
        initComponents();
        inits();
        initEvents();
    }

    @Override
    public void exibir(List<SchemaNfe> lista) {
        tmSchemaNfe.novaLista(lista);
        JTableAjusteLarguraColunas.ajustar(jtSchemaNfe);
        tcrAltura.ajustar();
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

    @Override
    public void notificar(String msg) {
        showMessageDialog(this, msg);
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

        manual.salvarConfiguracoes();

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

                manual.carregarConfiguracoes();
                manual.listarTodos();
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
                    manual.listarPorFiltro(jtfFiltro.getText());
                }
            }
        });

        jtfFiltro.getDocument().addDocumentListener(new DocumentListenerAdapter() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                manual.listarTodos();
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
                manual.listarPorFiltro(jtfFiltro.getText());
            }
        });

        jtbOcorrenciaPalavra.addActionListener(actionEvent -> {
            if (!jtfFiltro.getText().trim().equals("")) {
                manual.listarPorFiltro(jtfFiltro.getText());
            }
        });

        jlInformacao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                final JDSobre jdSobre = new JDSobre(JFGuiNFe.this);
                jdSobre.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jlInformacao.setBorder(lbBlack);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                jlInformacao.setBorder(null);
            }
        });
    }

    private JComboBox<EFiltro> jcbFiltro;
    private JTextField jtfFiltro;
    private JToggleButton jtbMatchCase;
    private JToggleButton jtbOcorrenciaPalavra;
    private JLabel jlInformacao;
    private ImageIcon icInfo = new ImageIcon(getClass().getResource("/img/information-20px.png"));
    private LineBorder lbBlack = new LineBorder(Color.BLACK, 1);
    private JScrollPane jspSchemaNfe;
    private JTable jtSchemaNfe;
    private JTable jtSchemaNfeLateral;
    private JLabel jlAtualizacaoTecnica;
    private JLabel jlVersao;

    public void initComponents() {
        setTitle("GuiNF-e");
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

        jlInformacao = new JLabel(icInfo);

        jtSchemaNfe = new JTable();
        jtSchemaNfe.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jspSchemaNfe = new JScrollPane(jtSchemaNfe);
        JTableAjusteLarguraColunas.alinharHeader(jtSchemaNfe);

        final Font font = new Font("Dialog", Font.BOLD, 15);
        jlAtualizacaoTecnica = new JLabel(Recursos.get("jlatualizacaotecnica", Sistema.ATUALIZACAO_TECNICA));
        jlAtualizacaoTecnica.setFont(font);
        jlVersao = new JLabel(Recursos.get("jlversao"));
        jlVersao.setFont(font);

        jtSchemaNfeLateral = new JTable();
        jtSchemaNfeLateral.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jtSchemaNfeLateral.setPreferredScrollableViewportSize(new Dimension(75, 0));
        jtSchemaNfeLateral.setDefaultRenderer(Object.class, new TableCellRendererHeader(jtSchemaNfe));
        jspSchemaNfe.setRowHeaderView(jtSchemaNfeLateral);

        jpTopII.setLayout(new BorderLayout());

        final JPanel jpTopPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpTopPesquisa.add(jcbFiltro);
        jpTopPesquisa.add(jtfFiltro);
        jpTopPesquisa.add(jtbMatchCase);
        jpTopPesquisa.add(jtbOcorrenciaPalavra);

        final JPanel jpTopInformacao = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        jpTopInformacao.add(jlInformacao);

        jpTopII.add(jpTopPesquisa, BorderLayout.WEST);
        jpTopII.add(jpTopInformacao, BorderLayout.EAST);

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
