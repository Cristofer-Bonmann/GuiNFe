package guinfe.util;

import guinfe.entity.SchemaNfe;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Classe reponsável por aplicar HTML no conteúdo das células de uma coluna específica de uma JTable. Também substitui
 * o caractere de quebra de linha do Java("\\n") para quebra de linha do HTML(br) na coluna de n.º8 e altera a cor das células.
 *
 * <pre>
 *   Como usar:
 *
 *   final {@link JTable} jTable = new JTable();
 *   final {@link TableCellRendererHtml} htmlRenderer = new HtmlRenderer();
 *   jTable.getColumnModel().getColumn(9).setCellRenderer(htmlRenderer);
 * </pre>
 */
public class TableCellRendererHtml extends DefaultTableCellRenderer {

  /**
   * Aplica formatação HTML no conteúdo das células com quebra de linha e coloração de linhas.
   *
   * @param table  the <code>JTable</code>
   * @param value  the value to assign to the cell at
   *                  <code>[row, column]</code>
   * @param isSelected true if cell is selected
   * @param hasFocus true if cell has focus
   * @param row  the row of the cell to render
   * @param column the column of the cell to render
   *
   * @return Component.
   */
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    final guinfe.util.TableModel tableModel = (guinfe.util.TableModel) table.getModel();
    final SchemaNfe schemaNfe = (SchemaNfe) tableModel.getValue(row);

    if (schemaNfe != null && schemaNfe.getIdGrupo().equals("ID")) {
      label.setBackground(Color.LIGHT_GRAY);

    } else if(schemaNfe != null && schemaNfe.getElemento().equals("G")) {
      label.setBackground(new Color(253, 222, 118));

    } else {
      if (isSelected) {
        label.setBackground(new Color(168, 194, 255));
      } else {
        label.setBackground(Color.WHITE);
      }
    }

    if (column == 8) {
      label.setText("<html>" + value.toString().replace("\\n", "<br>") + "</html>");
    }

    return label;
  }
}
