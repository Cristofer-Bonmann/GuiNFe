package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.view.ManualView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
  public void deveSalvarConfiguracoes() throws IOException {
    final EFiltro eFiltro = EFiltro.IDGRUPO;
    final boolean matchCase = true;
    final boolean ocorrenciaLetra = true;
    final Properties properties = mock(Properties.class);

    when(view.getSelectedEFiltro()).thenReturn(eFiltro);
    when(view.isMatchCase()).thenReturn(matchCase);
    when(view.isOcorrenciaPalavra()).thenReturn(ocorrenciaLetra);
    doReturn(properties).when(manual).getProperties();
    doNothing().when(manual).confStore(properties, eFiltro.name(), matchCase, ocorrenciaLetra);
    manual.salvarConfiguracoes();

    verify(view).getSelectedEFiltro();
    verify(view).isMatchCase();
    verify(view).isOcorrenciaPalavra();
    verify(manual).getProperties();
    verify(manual).confStore(properties, eFiltro.name(), matchCase, ocorrenciaLetra);
    verifyNoMoreInteractions(view);
  }

  @Test
  public void deveCarregarConfiguracoes() throws IOException {
    final boolean existe = true;

    doReturn(existe).when(manual).arquivoPropertiesExiste();
    manual.carregarConfiguracoes();

    verify(manual).arquivoPropertiesExiste();
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
  public void deveListarTodosRegistrosComFiltroVazio() throws SQLException {
    final String filtro = "";
    final List list = mock(List.class);

    when(daoPresenter.listar()).thenReturn(list);
    doNothing().when(view).exibir(list);
    manual.listarPorFiltro(filtro);

    verify(daoPresenter).listar();
    verify(view).exibir(list);
    verifyNoMoreInteractions(daoPresenter, view);
  }

  @Test
  public void deveListarPorFiltro() throws SQLException {
    final EFiltro eFiltro = EFiltro.IDGRUPO;
    final String filtro = "termo do filtro";
    final List<SchemaNfe> lista = Mockito.mock(List.class);
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    when(view.getSelectedEFiltro()).thenReturn(eFiltro);
    when(view.isMatchCase()).thenReturn(matchCase);
    when(view.isOcorrenciaPalavra()).thenReturn(ocorrenciaPalavra);
    when(daoPresenter.listar(eFiltro, filtro, matchCase, ocorrenciaPalavra)).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listarPorFiltro(filtro);

    verify(view).getSelectedEFiltro();
    verify(view).isMatchCase();
    verify(view).isOcorrenciaPalavra();
    verify(daoPresenter).listar(eFiltro, filtro, matchCase, ocorrenciaPalavra);
    verify(view).exibir(lista);
    verifyNoMoreInteractions(daoPresenter, view);
  }

  @Test
  public void deveListarTodos() throws SQLException {
    final String filtro = "";
    final List<SchemaNfe> lista = mock(List.class);

    when(view.getFiltro()).thenReturn(filtro);
    when(daoPresenter.listar()).thenReturn(lista);
    doNothing().when(view).exibir(lista);
    manual.listarTodos();

    verify(view).getFiltro();
    verify(daoPresenter).listar();
    verify(view).exibir(lista);
    verifyNoMoreInteractions(daoPresenter, view);
  }
}
