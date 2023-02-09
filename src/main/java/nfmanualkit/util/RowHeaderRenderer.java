package nfmanualkit.util;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// TODO: 09/02/2023 inserir doc
public class RowHeaderRenderer extends JLabel implements TableCellRenderer {

  // TODO: 09/02/2023 inserir doc
  public RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(javax.swing.BorderFactory.createEmptyBorder());
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  // TODO: 09/02/2023 inserir doc
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, int row, int column) {
    setText((value == null) ? "" : value.toString());
    return this;
  }
}
