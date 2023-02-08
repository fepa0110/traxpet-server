package stateless;
import java.util.Collection;
import model.Logro;
public interface LogroService {
    public Collection<Logro> findAll();
    public Logro findById(long id);
}
