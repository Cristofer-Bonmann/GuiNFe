package nfmanualkit.main;

import nfmanualkit.entity.SchemaNfe;
import nfmanualkit.enumeracao.EFiltro;
import nfmanualkit.presenter.DaoPresenter;
import nfmanualkit.connection.Dao;
import nfmanualkit.util.Recursos;
import nfmanualkit.view.ManualView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 * Classe responsável por acessar e enviar dados do {@link Dao} para o {@link ManualView}.
 */
public class Manual {

  private DaoPresenter daoPresenter;

  private ManualView view;

  /**
   * Contrutor da classe. Instância {@link DaoPresenter}.
   */
  public Manual() {
    this.daoPresenter = new Dao();
  }

  /**
   * Atribuí o {@link ManualView}.
   * @param view objeto {@link ManualView}.
   */
  public void setView(ManualView view) {
    this.view = view;
  }


  /**
   * Exibe a lista com todos os registros de {@link SchemaNfe} do banco de dados na View.<br>
   * A operação só será efetuada se o conteúdo de 'filtro' for igual de uma String vazia("").
   *
   * @return lista de objetos {@link SchemaNfe}.
   *
   * @throws SQLException
   */
  public void listarTodos() throws SQLException {
    final String filtro = view.getFiltro();
    if (filtro.trim().equals("")) {
      final List<SchemaNfe> lista = daoPresenter.listar();
      view.exibir(lista);
    }
  }

  /**
   * Exibe a lista de {@link SchemaNfe} filtrada pelo termo(parâmetro filtro) na View.<br>
   * A operação só será efetuada se o conteúdo de 'filtro' deferir de uma String vazia("").
   *
   * @param filtro termo que filtrará o resultado.
   *
   * @throws SQLException
   */
  public void listarPorFiltro(String filtro) {
    try {
      final List<SchemaNfe> lista;

      if (!filtro.trim().equals("")) {
        final EFiltro eFiltro = view.getSelectedEFiltro();
        final boolean matchCase = view.isMatchCase();
        final boolean ocorrenciaPalavra = view.isOcorrenciaPalavra();
        lista = daoPresenter.listar(eFiltro, filtro, matchCase, ocorrenciaPalavra);

      } else {
        lista = daoPresenter.listar();
      }

      view.exibir(lista);

    } catch(SQLException sqle) {
      view.notificar(Recursos.get("erro", sqle.getMessage()));
    }
  }

  /**
   * Gera uma matriz de apenas uma coluna e com a quantidade de linhas igual ao tamanho da lista(parâmetro). <br>
   * O conteúdo das linhas será o respectivo valor de 'idGrupo' lista passada por parâmetro.
   *
   * @param lista lista de registros {@link SchemaNfe};
   * @return matriz tamanho da lista x 1.
   */
  public Object[][] getMatrizIdGrupo(List<SchemaNfe> lista) {
    final Object[][] data = new Object[lista.size()][1];

    IntStream.range(0, lista.size()).forEach(index -> {
      final SchemaNfe schemaNfe = lista.get(index);
      final String idGrupo = schemaNfe.getIdGrupo();

      data[index][0] = idGrupo;
    });

    return data;
  }

  /**
   * @return retorna vetor com tamanho 1 com o valor de uma String vazia("").
   */
  public Object[] getVetorColunaIdGrupo() {
    final Object[] columnNames = new Object[1];
    columnNames[0] = "";
    return columnNames;
  }

  /**
   * Recebe o título do 'header' da JTable, retorna o enumeration {@link EFiltro} correspondente ao 'nomeColuna' e
   * seleciona na View, o item correspondente a teste enumeration.
   *
   * @param nomeColuna o nome da coluna(header do JTable). Podendo ser qualquer qualquer propriedade 'rotulo' do
   *                   enumeration {@link EFiltro}.
   */
  public void selecionarFiltro(String nomeColuna) {
    final EFiltro eFiltro = EFiltro.getPorRotulo(nomeColuna);
    view.setSelectedEFiltro(eFiltro);
  }

  /**
   * Lê e carrega o arquivo 'conf.properties' localizado na raíz do projeto.
   *
   * @return objeto Proprieties.
   *
   * @throws IOException
   */
  protected Properties getProperties() throws IOException {
    final FileInputStream fisConf = new FileInputStream(Sistema.PATH_CONF);

    final Properties properties = new Properties();
    properties.load(fisConf);

    return properties;
  }

  /**
   * Atribui nos respectivos componentes da {@link ManualView} os valores do arquivo 'conf.properties'. <br>
   * Os valores são: filtro_selecionado, matchcase e ocorrencia_letra. <br>
   *
   * As configurações só serão carregadas se o arquivo properties existir.
   *
   * @throws IOException
   */
  public void carregarConfiguracoes() throws IOException {
    final boolean propertiesExiste = verifArquivoProperties();
    if (propertiesExiste) {
      final Properties properties = getProperties();

      final EFiltro eFiltro = EFiltro.valueOf(properties.get("filtro_selecionado").toString());
      final boolean matchCase = Boolean.parseBoolean(properties.get("matchcase").toString());
      final boolean ocorrenciaLetra = Boolean.parseBoolean(properties.get("ocorrencia_letra").toString());

      view.setSelectedEFiltro(eFiltro);
      view.setMatchCaseSelected(matchCase);
      view.setOcorrenciaLetraSelected(ocorrenciaLetra);
    }
  }

  /**
   * Grava no arquivo {@link Properties}(conf.properties) os valores dos parâmetros(filtro, matchCase e ocorrenciaPalavra)
   * passados.
   *
   * @param properties arquivo 'conf.properties' localizado na raíz do projeto.
   * @param filtro proprieadde 'name' de um {@link EFiltro};
   * @param matchCase se pesquisa está definida como 'matchCase'.
   * @param ocorrenciaPalavra se a pesquisa está definida como 'ocorrenciaPalavra'.
   * @throws IOException
   */
  protected void confStore(Properties properties, String filtro, boolean matchCase, boolean ocorrenciaPalavra) throws IOException {
    properties.setProperty("filtro_selecionado", filtro);
    properties.setProperty("matchcase", String.valueOf(matchCase));
    properties.setProperty("ocorrencia_letra", String.valueOf(ocorrenciaPalavra));

    FileOutputStream fos = new FileOutputStream(Sistema.PATH_CONF);
    properties.store(fos, "'conf.properties' atualizado.");
  }

  /**
   * Grava as configurações no arquivo 'conf.properties'.
   * @throws IOException
   */
  public void salvarConfiguracoes() {
    try {
      final EFiltro eFiltro = view.getSelectedEFiltro();
      final boolean matchCase = view.isMatchCase();
      final boolean ocorrenciaPalavra = view.isOcorrenciaPalavra();

      final Properties properties = getProperties();
      confStore(properties, eFiltro.name(), matchCase, ocorrenciaPalavra);

    } catch (IOException ioe) {
      view.notificar(Recursos.get("erro", ioe.getMessage()));
    }
  }

  /**
   * Verifica se o arquivo 'conf.properties' existe no diretório raíz do projeto.
   * @return true = arquivo existe, false caso contrário.
   */
  public boolean verifArquivoProperties() throws IOException {
    final String path = Sistema.PATH_CONF;
    final File confProperties = new File(path);

    if (!confProperties.exists()) {
      confProperties.createNewFile();
    }

    return confProperties.exists();
  }
}
