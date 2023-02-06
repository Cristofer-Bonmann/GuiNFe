package nfmanualkit.connection;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ConnectTest {

  @Spy
  private nfmanualkit.connection.Connect connect;

  @Before
  public void setUp(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void deveExistirScript() {
    final URL resource = getClass().getClassLoader().getResource("script/script_schema_nfe.sql");

    assertThat(resource, CoreMatchers.notNullValue());
  }

  @Test
  public void naoDeveConectarBancoDeDados() throws SQLException {
    final String dir = System.getProperty("user.dir") + File.separator + "db";
    final String url = "jdbc:sqlite:" + dir + "/database.db";

    doThrow(SQLException.class).when(connect).conectar(url);
    connect.conectarBancoDeDados();

    verify(connect).conectar(url);
  }

  @Test
  public void deveConectarBancoDeDados() throws SQLException {
    final String url = "";
    final Connection connection = mock(Connection.class);

    doReturn(connection).when(connect).conectar(url);
    final Connection connectionResult = connect.conectarBancoDeDados();

    assertThat(connectionResult, notNullValue());
  }
}
