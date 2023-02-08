package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.view.ManualView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

public class ManualTest {

  @Spy
  @InjectMocks
  private Manual manual;

  @Mock
  private DaoPresenter daoPresenter;

  @Mock
  private ManualView view;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveListarPorFiltro() throws SQLException {
    final String filtro = "";
    final List<SchemaNfe> lista = Mockito.mock(List.class);

    when(daoPresenter.listar(filtro)).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listarPorFiltro(filtro);

    verify(daoPresenter).listar(filtro);
    verify(view).exibir(lista);
    verifyNoMoreInteractions(daoPresenter, view);
  }

  @Test
  public void deveListarTodos() throws SQLException {
    final List<SchemaNfe> lista = mock(List.class);

    when(daoPresenter.listar()).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listarTodos();

    verify(daoPresenter).listar();
    verify(view).exibir(lista);
    verifyNoMoreInteractions(daoPresenter, view);
  }
}
