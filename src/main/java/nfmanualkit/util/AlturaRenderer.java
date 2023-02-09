package nfmanualkit.util;

import java.awt.*;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Classe responsável por redimensionar a altura das linha de um componente {@link JTable} dinâmicamente.
 */
public class AlturaRenderer implements TableCellRenderer {
  private final JTable table;

  /**
   * Construtor da classe. Recebe o componente que será renderizado.
   * @param table componente {@link JTable}.
   */
  public AlturaRenderer(JTable table) {
    this.table = table;
  }

  /**
   * Renderiza a altura da linha do componente {@link JTable}. A altura adotada será a maior altura encontrada nas células
   * da linha. <br>
   * JavaDoc original do {@link TableCellRenderer}:<br>
   *
   * @param table           the <code>JTable</code> that is asking the
   *                          renderer to draw; can be <code>null</code>
   * @param value           the value of the cell to be rendered.  It is
   *                          up to the specific renderer to interpret
   *                          and draw the value.  For example, if
   *                          <code>value</code>
   *                          is the string "true", it could be rendered as a
   *                          string or it could be rendered as a check
   *                          box that is checked.  <code>null</code> is a
   *                          valid value
   * @param isSelected      true if the cell is to be rendered with the
   *                          selection highlighted; otherwise false
   * @param hasFocus        if true, render cell appropriately.  For
   *                          example, put a special border on the cell, if
   *                          the cell can be edited, render in the color used
   *                          to indicate editing
   * @param row             the row index of the cell being drawn.  When
   *                          drawing the header, the value of
   *                          <code>row</code> is -1
   * @param column          the column index of the cell being drawn
   *
   * @return Component
   */
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

    // Obtém o renderizador padrão da célula
    final TableCellRenderer renderer = table.getDefaultRenderer(table.getColumnClass(column));
    final Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    // Ajusta a altura da linha de acordo com o conteúdo
    int height = c.getPreferredSize().height;
    if (height > table.getRowHeight(row)) {
      table.setRowHeight(row, height);
    }

    return c;
  }

  /**
   * Redimensiona a altura das células de todas as linhas.
   * <p>
   * Percorre todas as linhas de um componente {@link JTable}. Para cada linha será identificada qual é a célula com a
   * maior altura e aplica essa altura para todas as cálulas da linha.
   * </p>
   */
  public void adjustRowHeights() {

    for (int row = 0; row < table.getRowCount(); row++) {
      int height = table.getRowHeight();

      for (int column = 0; column < table.getColumnCount(); column++) {
        final TableCellRenderer renderer = table.getCellRenderer(row, column);
        final Component c = table.prepareRenderer(renderer, row, column);
        final int h = c.getPreferredSize().height;
        height = Math.max(height, h);
      }

      table.setRowHeight(row, height);
    }
  }
}

