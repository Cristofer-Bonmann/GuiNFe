package nfmanualkit.presenter;

import nfmanualkit.entity.SchemaNfe;

import java.util.List;

public interface DaoPresenter {

  List<SchemaNfe> listar(String filtro);
}
