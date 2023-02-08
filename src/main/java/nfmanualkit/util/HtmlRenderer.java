package nfmanualkit.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

// TODO: 08/02/2023 inserir doc
public class HtmlRenderer extends DefaultTableCellRenderer {

  // TODO: 08/02/2023 inserir doc.
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    label.setText("<html>" + value.toString().replace("\\n", "<br>") + "</html>");

    return label;
  }
}
