package guinfe.enumeracao;

public enum EFiltro {
  TODOS("", "Todos"),
  IDGRUPO("idGrupo", "ID Grupo"),
  CAMPO("campo", "Campo"),
  DESCRICAO("descricao", "Descrição"),
  ELEMENTO("elemento", "Elemento"),
  PAI("pai", "Pai"),
  TIPO("tipo", "Tipo"),
  OCORRENCIA("ocorrencia", "Ocorrência"),
  TAMANHO("tamanho", "Tamanho"),
  OBSERVACAO("observacao", "Observações");

  private String filtro;
  private String rotulo;

  EFiltro(String filtro, String rotulo) {
    this.filtro = filtro;
    this.rotulo = rotulo;
  }

  /**
   * @param rotulo String que representa a propriedade 'rotulo' do {@link EFiltro}.
   *
   * @return retorna um {@link EFiltro} correspondete ao 'rotulo'(parâmetro). <br>
   * Se não for encontrado nenhum {@link EFiltro} correspondente será retornado EFiltro.TODOS.
   */
  public static EFiltro getPorRotulo(String rotulo) {
    EFiltro eFiltroRotulo = null;

    for (EFiltro eFiltro : EFiltro.values()) {
      if (eFiltro.getRotulo().equals(rotulo)) {
        eFiltroRotulo = eFiltro;
        break;
      }
    }

    eFiltroRotulo = eFiltroRotulo == null ? EFiltro.TODOS : eFiltroRotulo;

    return eFiltroRotulo;
  }

  public String getFiltro() {
    return this.filtro;
  }

  /**
   * Recebe uma variável booleana(matchCade) que define se a propriedade 'filtro' do {@link EFiltro} será formata
   * 'lower(valor_filtro)', quando false, ou apenas 'valorFiltro' quando true.
   * @param matchCase
   * @return propriedade 'filtro' formatada.
   */
  public String getFiltro(boolean matchCase) {
    String f = !matchCase ? "lower(" + this.filtro + ")" : filtro;
    return f;
  }

  public String getRotulo() {
    return this.rotulo;
  }

  @Override
  public String toString() {
    return this.rotulo;
  }
}
