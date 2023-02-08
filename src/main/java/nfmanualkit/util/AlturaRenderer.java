package nfmanualkit.util;

import java.awt.*;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

// TODO: 08/02/2023 inserir doc
public class AlturaRenderer implements TableCellRenderer {
  private final JTable table;

  // TODO: 08/02/2023 inserir doc
  public AlturaRenderer(JTable table) {
    this.table = table;
  }

  // TODO: 08/02/2023 inserir doc
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

  // TODO: 08/02/2023 inserir doc
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

