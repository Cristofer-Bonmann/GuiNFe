package nfmanualkit.connection;

import nfmanualkit.enumeracao.EFiltro;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DaoTest {

  @Spy
  private Dao dao;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveMontarQueryFiltroObservacao() {
    final String query = dao.montarQuery(EFiltro.OBSERVACAO);

    final String esperado = "SELECT * FROM schema_nfe WHERE observacao LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroTamanho() {
    final String query = dao.montarQuery(EFiltro.TAMANHO);

    final String esperado = "SELECT * FROM schema_nfe WHERE tamanho LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroOcorrencia() {
    final String query = dao.montarQuery(EFiltro.OCORRENCIA);

    final String esperado = "SELECT * FROM schema_nfe WHERE ocorrencia LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroTipo() {
    final String query = dao.montarQuery(EFiltro.TIPO);

    final String esperado = "SELECT * FROM schema_nfe WHERE tipo LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroPai() {
    final String query = dao.montarQuery(EFiltro.PAI);

    final String esperado = "SELECT * FROM schema_nfe WHERE pai LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroElemento() {
    final String query = dao.montarQuery(EFiltro.ELEMENTO);

    final String esperado = "SELECT * FROM schema_nfe WHERE elemento LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroDescricao() {
    final String query = dao.montarQuery(EFiltro.DESCRICAO);

    final String esperado = "SELECT * FROM schema_nfe WHERE descricao LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroCampo() {
    final String query = dao.montarQuery(EFiltro.CAMPO);

    final String esperado = "SELECT * FROM schema_nfe WHERE campo LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroIdGrupo() {
    final String query = dao.montarQuery(EFiltro.IDGRUPO);

    final String esperado = "SELECT * FROM schema_nfe WHERE idGrupo LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }
}
