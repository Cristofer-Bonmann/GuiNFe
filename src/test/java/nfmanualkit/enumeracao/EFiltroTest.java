package nfmanualkit.enumeracao;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EFiltroTest {
    
    @Test
    public void deveRetornarFiltroComFuncSQLLower() {
        final String filtroIdGrupo = EFiltro.IDGRUPO.getFiltroSQLLower();
        final String filtroCampo = EFiltro.CAMPO.getFiltroSQLLower();
        final String filtroDescricao = EFiltro.DESCRICAO.getFiltroSQLLower();
        final String filtroElemento = EFiltro.ELEMENTO.getFiltroSQLLower();
        final String filtroPai = EFiltro.PAI.getFiltroSQLLower();
        final String filtroTipo = EFiltro.TIPO.getFiltroSQLLower();
        final String filtroOcorrencia = EFiltro.OCORRENCIA.getFiltroSQLLower();
        final String filtroTamanho = EFiltro.TAMANHO.getFiltroSQLLower();
        final String filtroObservacao = EFiltro.OBSERVACAO.getFiltroSQLLower();

        assertThat(filtroIdGrupo, is("lower(idGrupo)"));
        assertThat(filtroCampo, is("lower(campo)"));
        assertThat(filtroDescricao, is("lower(descricao)"));
        assertThat(filtroElemento, is("lower(elemento)"));
        assertThat(filtroPai, is("lower(pai)"));
        assertThat(filtroTipo, is("lower(tipo)"));
        assertThat(filtroOcorrencia, is("lower(ocorrencia)"));
        assertThat(filtroTamanho, is("lower(tamanho)"));
        assertThat(filtroObservacao, is("lower(observacao)"));
    }

    @Test
    public void deveRetornarFiltro() {
        final String filtroIdGrupo = EFiltro.IDGRUPO.getFiltro();
        final String filtroCampo = EFiltro.CAMPO.getFiltro();
        final String filtroDescricao = EFiltro.DESCRICAO.getFiltro();
        final String filtroElemento = EFiltro.ELEMENTO.getFiltro();
        final String filtroPai = EFiltro.PAI.getFiltro();
        final String filtroTipo = EFiltro.TIPO.getFiltro();
        final String filtroOcorrencia = EFiltro.OCORRENCIA.getFiltro();
        final String filtroTamanho = EFiltro.TAMANHO.getFiltro();
        final String filtroObservacao = EFiltro.OBSERVACAO.getFiltro();

        assertThat(filtroIdGrupo, is("idGrupo"));
        assertThat(filtroCampo, is("campo"));
        assertThat(filtroDescricao, is("descricao"));
        assertThat(filtroElemento, is("elemento"));
        assertThat(filtroPai, is("pai"));
        assertThat(filtroTipo, is("tipo"));
        assertThat(filtroOcorrencia, is("ocorrencia"));
        assertThat(filtroTamanho, is("tamanho"));
        assertThat(filtroObservacao, is("observacao"));
    }
}
