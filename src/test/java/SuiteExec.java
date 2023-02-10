import nfmanualkit.connection.ConnectTest;
import nfmanualkit.connection.DaoTest;
import nfmanualkit.main.ManualTest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConnectTest.class,
        DaoTest.class,
        ManualTest.class,
})
public class SuiteExec {

  @BeforeClass
  public static void setUp() {
    System.out.println("Iniciando testes...");
  }
}
