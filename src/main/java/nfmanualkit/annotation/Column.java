package nfmanualkit.annotation;

import nfmanualkit.enumeracao.EFiltro;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// TODO: 10/02/2023 inserir doc
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

  EFiltro nomeColuna() default EFiltro.TODOS;
}
