package nfmanualkit.enumeracao;

public enum EFiltro {
  IDGRUPO("idGrupo"),
  CAMPO("campo"),
  DESCRICAO("descricao"),
  ELEMENTO("elemento"),
  PAI("pai"),
  TIPO("tipo"),
  OCORRENCIA("ocorrencia"),
  TAMANHO("tamanho"),
  OBSERVACAO("observacao");

  private String filtro;

  EFiltro(String filtro) {
    this.filtro = filtro;
  }

  public String getFiltro() {
    return this.filtro;
  }

  }
