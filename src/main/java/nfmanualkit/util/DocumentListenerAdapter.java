package nfmanualkit.util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentListenerAdapter implements DocumentListener {
  @Override
  public void insertUpdate(DocumentEvent e) {}

  @Override
  public void removeUpdate(DocumentEvent e) {}

  @Override
  public void changedUpdate(DocumentEvent e) {}
}
