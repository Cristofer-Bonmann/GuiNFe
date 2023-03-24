package guinfe.util;

import guinfe.annotation.Column;
import guinfe.enumeracao.EFiltro;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Classe que extende de {@link AbstractTableModel} e é responsavel para ser o model de um componente JTable.
 * </p>
 * <br>
 * <p>
 * Como usar: supondo que exista uma classe chamada 'Usuario' com o seu campo 'nome' marcado com o Annotation {@link Column}.
 * </p>
 *
 * <pre>
 *     final {@link TableModel}<Usuario> tableModel = new TableModel(Usuario.class);
 *     final {@link JTable} jTable = new JTable();
 *     jTable.setModel(tableModel);
 * </pre>
 * @param <T>
 */
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
    return Arrays.asList(aClass.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Column.class))
            .collect(Collectors.toList());
  }

  @Override
  public String getColumnName(int columnIndex) {
    String nomeColumna = "";

    final Field field = getFields().get(columnIndex);
    final boolean annotationPresent = field.isAnnotationPresent(Column.class);

    if (annotationPresent) {
      final Column annotation = field.getAnnotation(Column.class);
      final EFiltro eFiltro = annotation.nomeColuna();
      nomeColumna = eFiltro.getRotulo();
    }

    return nomeColumna;
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

  /**
   * Atribuí uma nova lista neste model e dispara o evento de atualização dos dados da tabela.
   *
   * @param lista lista de itens.
   */
  public void novaLista(List<T> lista) {
    this.list = lista;
    fireTableDataChanged();
  }

  /**
   * Retorna o objeto da lista de um índice específico.
   *
   * @param row índice da lista.
   * @return objeto da lista.
   */
  public T getValue(int row) {
    return list.get(row);
  }
}
