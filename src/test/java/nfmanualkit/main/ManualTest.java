package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.view.ManualView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

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
  public void deveListarPorFiltro() {
    final String filtro = "";
    final List<SchemaNfe> lista = Mockito.mock(List.class);

    when(daoPresenter.listar(filtro)).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listar(filtro);

    verify(daoPresenter).listar(filtro);
    verify(view).exibir(lista);
    verifyNoMoreInteractions(daoPresenter, view);
  }
}
