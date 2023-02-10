package nfmanualkit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nfmanualkit.annotation.Column;
import nfmanualkit.enumeracao.EFiltro;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class SchemaNfe {

  @Builder.Default
  private Integer id = 0;

  @Builder.Default
  @Column(nomeColuna = EFiltro.IDGRUPO)
  private String idGrupo = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.CAMPO)
  private String campo = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.DESCRICAO)
  private String descricao = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.ELEMENTO)
  private String elemento = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.PAI)
  private String pai = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.TIPO)
  private String tipo = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.OCORRENCIA)
  private String ocorrencia = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.TAMANHO)
  private String tamanho = "";

  @Builder.Default
  @Column(nomeColuna = EFiltro.OBSERVACAO)
  private String observacao = "";
}
