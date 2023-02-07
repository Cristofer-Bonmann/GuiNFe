package nfmanualkit.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableModel<T> extends AbstractTableModel {

  private Class<?> aClass;
  private List<T> list;

  public TableModel(Class<?> aClass) {
    this.aClass = aClass;
    this.list = new ArrayList<>();
  }

  @Override
  public int getRowCount() {
    return list.size();
  }

  @Override
  public int getColumnCount() {
    return getFields().size();
  }

  private List<Field> getFields() {
    return Arrays.asList(aClass.getDeclaredFields());
  }

  @Override
  public String getColumnName(int columnIndex) {
    return getFields().get(columnIndex).getName();
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return getFields().get(columnIndex).getClass();
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    final T object = list.get(rowIndex);
    final Field field = getFields().get(columnIndex);
    field.setAccessible(true);

    Object value = "";
    try {
      value = field.get(object);
    } catch (IllegalAccessException e) {
      System.err.println("TableModel | getValueAt | Não foi possível retornar o valor:\n" + e.getMessage());
    }

    return value;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    final T object = list.get(rowIndex);
    final Field field = getFields().get(columnIndex);
    field.setAccessible(true);

    try {
      field.set(object, aValue);
    } catch (IllegalAccessException e) {
      System.err.println("TableModel | setValueAt | Não foi possível atribuir o valor:\n" + e.getMessage());
    }
  }

  public void novaLista(List<T> lista) {
    this.list = lista;
    fireTableDataChanged();
  }
}
