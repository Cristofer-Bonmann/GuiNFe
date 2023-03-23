import guinfe.connection.ConnectTest;
import guinfe.connection.DaoTest;
import guinfe.enumeracao.EFiltroTest;
import guinfe.main.ManualTest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConnectTest.class,
        DaoTest.class,
        EFiltroTest.class,
        ManualTest.class,
})
public class SuiteExec {

  @BeforeClass
  public static void setUp() {
    System.out.println("Iniciando testes...");
  }
}
