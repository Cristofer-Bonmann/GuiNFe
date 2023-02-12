package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.view.ManualView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
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
  public void deveSelecionarFiltroNENHUM() {
    final String nomeColuna = "rotulo desconhecido";

    doNothing().when(view).setSelectedEFiltro(EFiltro.TODOS);
    manual.selecionarFiltro(nomeColuna);

    verify(view).setSelectedEFiltro(EFiltro.TODOS);
    verifyNoMoreInteractions(view);
  }

  @Test
  public void deveSelecionarFiltro() {
    final String nomeColuna = EFiltro.IDGRUPO.getRotulo();

    doNothing().when(view).setSelectedEFiltro(EFiltro.IDGRUPO);
    manual.selecionarFiltro(nomeColuna);

    verify(view).setSelectedEFiltro(EFiltro.IDGRUPO);
    verifyNoMoreInteractions(view);
  }

  @Test
  public void deveRetornarVetorColunaIdGrupo() {
    final Object[] columnName = manual.getVetorColunaIdGrupo();

    assertThat(columnName.length, is(1));
    assertThat(columnName[0], is(""));
  }

  @Test
  public void deveRetornarMatrizIdGrupo() {
    final List<SchemaNfe> lista = new ArrayList<>();
    lista.add(mock(SchemaNfe.class, Answers.RETURNS_MOCKS));

    final Object[][] matriz = manual.getMatrizIdGrupo(lista);

    assertThat(matriz.length, is(lista.size()));
    assertThat(matriz[0][0], notNullValue());
  }

  @Test
  public void deveListarPorFiltro() throws SQLException {
    final EFiltro eFiltro = EFiltro.IDGRUPO;
    final String filtro = "";
    final List<SchemaNfe> lista = Mockito.mock(List.class);

    when(view.getSelectedEFiltro()).thenReturn(eFiltro);
    when(daoPresenter.listar(eFiltro, filtro)).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listarPorFiltro(filtro);

    verify(view).getSelectedEFiltro();
    verify(daoPresenter).listar(eFiltro, filtro);
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
