package nfmanualkit.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Classe responsável por ajustar a largura para exibir o conteúdo completo da célula de uma JTable.
 * <p>
 *   Como usar:
 * </p>
 *
 * <pre>
 *   private JTable jTable = new JTable();
 *   {@link JTableAjusteColunas}.ajustarColunas(jTable);
 * </pre>
 */
public class JTableAjusteColunas {

  /**
   * Construtor privado da classe.
   */
  private JTableAjusteColunas(){}

  /**
   * Percorre as colunas de um {@link TableColumn} e verifica qual célula possue o conteúdo com a largura. Essa largura
   * será aplica nas demais células.
   *
   * @param jTable componente onde será aplicado e redimensionamento da largura.
   */
  public static void ajustarColunas(JTable jTable) {
    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    for (int column = 0; column < jTable.getColumnCount(); column++) {
      final TableColumn tableColumn = jTable.getColumnModel().getColumn(column);
      int preferredWidth = tableColumn.getMinWidth();
      final int maxWidth = tableColumn.getMaxWidth();

      for (int row = 0; row < jTable.getRowCount(); row++) {
        final TableCellRenderer cellRenderer = jTable.getCellRenderer(row, column);
        final Component c = jTable.prepareRenderer(cellRenderer, row, column);
        final int width = c.getPreferredSize().width + jTable.getIntercellSpacing().width;
        preferredWidth = Math.max(preferredWidth, width);

        if (preferredWidth >= maxWidth) {
          preferredWidth = maxWidth;
          break;
        }
      }

      tableColumn.setPreferredWidth(preferredWidth + 50);
    }
  }

  /**
   * Alinha o texto do header do JTable para a esquerda.
   * @param jTable componente com o {@link JTableHeader} que será alinhado.
   */
  public static void alinharHeader(JTable jTable) {
    final JTableHeader header = jTable.getTableHeader();
    final DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
    renderer.setHorizontalAlignment(SwingConstants.LEFT);
    header.setDefaultRenderer(renderer);
  }
}
