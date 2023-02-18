package nfmanualkit.enumeracao;

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

  // TODO: 10/02/2023 inserir doc
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

  public String getFiltroSQLLower() {
    return "lower(" + this.filtro + ")";
  }

  public String getRotulo() {
    return this.rotulo;
  }

  @Override
  public String toString() {
    return this.rotulo;
  }
}
