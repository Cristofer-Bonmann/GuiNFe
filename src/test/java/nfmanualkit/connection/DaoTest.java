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
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.OBSERVACAO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE observacao = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroTamanho() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.TAMANHO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE tamanho = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroOcorrencia() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.OCORRENCIA, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE ocorrencia = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroTipo() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.TIPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE tipo = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroPai() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.PAI, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE pai = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroElemento() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.ELEMENTO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE elemento = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroDescricao() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.DESCRICAO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE descricao = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroMatchCase() {
    final boolean matchCase = true;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.DESCRICAO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE lower(descricao) = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComOcorrenciaDePalavraComOutrosFiltros() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = true;

    final String query = dao.montarQuery(EFiltro.DESCRICAO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE descricao LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroCampoMatchCase() {
    final boolean matchCase = true;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.CAMPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE lower(campo) = ? OR lower(pai) = " +
            "(SELECT idGrupo FROM schema_nfe WHERE lower(campo) = ? ORDER BY id LIMIT 1)" +
            " ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComOcorrenciaDePalavraFiltroCampo() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = true;

    final String query = dao.montarQuery(EFiltro.CAMPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE campo LIKE ? OR pai LIKE " +
            "(SELECT idGrupo FROM schema_nfe WHERE campo LIKE ? ORDER BY id LIMIT 1)" +
            " ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroCampo() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.CAMPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE campo = ? OR pai = " +
            "(SELECT idGrupo FROM schema_nfe WHERE campo = ? ORDER BY id LIMIT 1)" +
            " ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComOcorrenciaDePalavraFiltroIdGrupo() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = true;

    final String query = dao.montarQuery(EFiltro.IDGRUPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE idGrupo LIKE ? OR pai LIKE ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroIdGrupoMatchCase() {
    final boolean matchCase = true;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.IDGRUPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE lower(idGrupo) = ? OR lower(pai) = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryFiltroIdGrupo() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.IDGRUPO, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE idGrupo = ? OR pai = ? ORDER BY id";
    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComOcorrenciaDePalavraComTodosFiltros() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = true;

    final String query = dao.montarQuery(EFiltro.TODOS, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE " +
            "idGrupo LIKE ? OR \n" +
            "campo LIKE ? OR \n" +
            "descricao LIKE ? OR \n" +
            "elemento LIKE ? OR \n" +
            "pai LIKE ? OR \n" +
            "tipo LIKE ? OR \n" +
            "ocorrencia LIKE ? OR \n" +
            "tamanho LIKE ? OR \n" +
            "observacao LIKE ? " +
            "ORDER BY id";

    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComTodosFiltrosMatchCase() {
    final boolean matchCase = true;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.TODOS, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE " +
            "lower(idGrupo) = ? OR \n" +
            "lower(campo) = ? OR \n" +
            "lower(descricao) = ? OR \n" +
            "lower(elemento) = ? OR \n" +
            "lower(pai) = ? OR \n" +
            "lower(tipo) = ? OR \n" +
            "lower(ocorrencia) = ? OR \n" +
            "lower(tamanho) = ? OR \n" +
            "lower(observacao) = ? " +
            "ORDER BY id";

    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarQueryComTodosFiltros() {
    final boolean matchCase = false;
    final boolean ocorrenciaPalavra = false;

    final String query = dao.montarQuery(EFiltro.TODOS, matchCase, ocorrenciaPalavra);

    final String esperado = "SELECT * FROM schema_nfe WHERE " +
            "idGrupo = ? OR \n" +
            "campo = ? OR \n" +
            "descricao = ? OR \n" +
            "elemento = ? OR \n" +
            "pai = ? OR \n" +
            "tipo = ? OR \n" +
            "ocorrencia = ? OR \n" +
            "tamanho = ? OR \n" +
            "observacao = ? " +
            "ORDER BY id";

    assertThat(query, is(esperado));
  }

  @Test
  public void deveMontarFiltroSemOcorrenciaDePalavra() {
    final String filtro = "filtro";
    final boolean ocorrenciaPalavra = false;

    final String filtroOcorrencia = dao.montarFiltroComOcorrenciaPalavra(filtro, ocorrenciaPalavra);

    assertThat(filtroOcorrencia, is("filtro"));
  }

  @Test
  public void deveMontarFiltroDeOcorrenciaDePalavra() {
    final String filtro = "filtro";
    final boolean ocorrenciaPalavra = true;

    final String filtroOcorrencia = dao.montarFiltroComOcorrenciaPalavra(filtro, ocorrenciaPalavra);

    assertThat(filtroOcorrencia, is("%filtro%"));
  }
}
