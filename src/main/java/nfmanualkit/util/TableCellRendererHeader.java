package nfmanualkit.util;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Classe responsável por renderizar a JTable de função 'header'.
 */
public class TableCellRendererHeader extends JLabel implements TableCellRenderer {

  /**
   * Contrutor da classe. Recebe um componente JTable para parâmetro e as propriedades 'foreground', 'background' e 'font'
   *  são aplicadas neste renderer.
   *
   * @param table componente {@link JTable};
   */
  public TableCellRendererHeader(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  /**
   * <p>
   *   Documentação original:
   * </p>
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
   * @return
   */
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    setText((value == null) ? "" : value.toString());

    return this;
  }
}
