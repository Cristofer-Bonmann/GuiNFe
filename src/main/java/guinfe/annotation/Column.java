package guinfe.annotation;

import guinfe.enumeracao.EFiltro;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * Interface Annotation para definir uma coluna para ser exibida em um componente JTable. Ex.:
 * </p>
 *
 * <pre>
 * @Column(nomeColuna = "Nome")
 *     private String nome;
 * </pre>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

  EFiltro nomeColuna() default EFiltro.TODOS;
}
