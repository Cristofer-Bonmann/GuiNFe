package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.util.Recursos;
import nfmanualkit.view.ManualView;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

  @BeforeClass
  public static void setUpClass() {
    criarConfProperties();
  }

  @AfterClass
  public static void teadDown() {
    criarConfProperties();
  }

  private static void criarConfProperties() {
    final Properties properties = new Properties();
    properties.setProperty("filtro_selecionado", EFiltro.TODOS.name());
    properties.setProperty("matchcase", "false");
    properties.setProperty("ocorrencia_letra", "true");
    final FileOutputStream fos;
    try {
      fos = new FileOutputStream(Sistema.PATH_CONF);
      properties.store(fos, "Arquivo 'conf.properties' foi criado!");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
  public void naoDeveSalvarConfiguracoes() throws IOException {
    final boolean existe = false;

    doReturn(existe).when(manual).verifArquivoProperties();
    manual.carregarConfiguracoes();

    verify(manual).verifArquivoProperties();
  }

  @Test
  public void deveCarregarConfiguracoes() throws IOException {
    criarConfProperties();
    final boolean existe = true;

    doReturn(existe).when(manual).verifArquivoProperties();
    manual.carregarConfiguracoes();

    verify(manual).verifArquivoProperties();
  }

  @Test
  public void deveCriarArquivoConfPropertiesInexistente() throws IOException {
    final File confProperties = new File(Sistema.PATH_CONF);
    confProperties.delete();

    final boolean existe = manual.verifArquivoProperties();

    assertThat(existe, is(true));
  }

  @Test
  public void deveExistirArquivoConfProperties() throws IOException {
    final boolean existe = manual.verifArquivoProperties();

    assertThat(existe, is(true));
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
  public void deveListarPorFiltroComSQLException() throws SQLException {
    final EFiltro eFiltro = EFiltro.IDGRUPO;
    final String filtro = "termo do filtro";
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;
    final SQLException sqlException = new SQLException("");
    final String msg = Recursos.get("erro", sqlException.getMessage());

    when(view.getSelectedEFiltro()).thenReturn(eFiltro);
    when(view.isMatchCase()).thenReturn(matchCase);
    when(view.isOcorrenciaPalavra()).thenReturn(ocorrenciaPalavra);
    when(daoPresenter.listar(eFiltro, filtro, matchCase, ocorrenciaPalavra)).thenThrow(sqlException);
    doNothing().when(view).notificar(msg);
    manual.listarPorFiltro(filtro);

    verify(view).getSelectedEFiltro();
    verify(view).isMatchCase();
    verify(view).isOcorrenciaPalavra();
    verify(daoPresenter).listar(eFiltro, filtro, matchCase, ocorrenciaPalavra);
    verify(view).notificar(msg);
    verifyNoMoreInteractions(daoPresenter, view);
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
