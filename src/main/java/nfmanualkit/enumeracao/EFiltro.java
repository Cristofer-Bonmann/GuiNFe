package nfmanualkit.enumeracao;

public enum EFiltro {
  IDGRUPO("idGrupo", "ID Grupo"),
  CAMPO("campo", "Campo"),
  DESCRICAO("descricao", "Descrição"),
  ELEMENTO("elemento", "Elemento"),
  PAI("pai", "Pai"),
  TIPO("tipo", "Tipo"),
  OCORRENCIA("ocorrencia", "Ocorrência"),
  TAMANHO("tamanho", "Tamanho"),
  OBSERVACAO("observacao", "Obervação");

  private String filtro;
  private String rotulo;

  EFiltro(String filtro, String rotulo) {
    this.filtro = filtro;
    this.rotulo = rotulo;
  }

  public String getFiltro() {
    return this.filtro;
  }

  @Override
  public String toString() {
    return this.rotulo;
  }

}
