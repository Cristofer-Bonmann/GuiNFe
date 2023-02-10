package nfmanualkit.annotation;

import nfmanualkit.enumeracao.EFiltro;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

  EFiltro nomeColuna() default EFiltro.NENHUM;
}
