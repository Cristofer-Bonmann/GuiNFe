package nfmanualkit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class SchemaNfe {

  @Builder.Default
  private Integer id = 0;

  @Builder.Default
  private String idGrupo = "";

  @Builder.Default
  private String campo = "";

  @Builder.Default
  private String descricao = "";

  @Builder.Default
  private String elemento = "";

  @Builder.Default
  private String pai = "";

  @Builder.Default
  private String tipo = "";

  @Builder.Default
  private String ocorrencia = "";

  @Builder.Default
  private String tamanho = "";

  @Builder.Default
  private String observacao = "";
}
