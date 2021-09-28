package stateless;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Caracteristica;
import model.Especie;
import model.Valor;
import servlet.ResponseMessage;
import stateless.CaracteristicaService;
import stateless.ValorService;

@Stateless
public class ValorServiceBean implements ValorService {

  @PersistenceContext(unitName = "traxpet")
  protected EntityManager em;

  @EJB
  CaracteristicaService serviceCaracteristica;

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public List<Valor> findByEspecie(Especie especie) {
    try {
      return getEntityManager()
        .createNamedQuery("Valor.findByEspecie", Valor.class)
        .setParameter("especie_nombre", especie.getNombre())
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Valor> findByCaracteristica(Caracteristica caracteristica) {
    try {
      return getEntityManager()
        .createNamedQuery("Valor.findByCaracteristica", Valor.class)
        .setParameter("caracteristica_nombre", caracteristica.getNombre())
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Valor> findByEspecieYCaracteristica(
    Especie especie,
    Caracteristica caracteristica
  ) {
    try {
      return em
        .createQuery(
          "select valor " +
          "from Valor valor " +
          "where valor.especie.nombre = :especie " +
          "and valor.caracteristica.nombre = :caracteristica",
          Valor.class
        )
        .setParameter("especie", especie.getNombre())
        .setParameter("caracteristica", caracteristica.getNombre())
        .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public String findCaracteristicasConValores(Especie especie) {
    Collection<Caracteristica> caracteristicas =
      this.serviceCaracteristica.findByEspecie(especie);
    String data =
      (
        "{\"caracteristica\":" +
        caracteristicas
          .stream()
          .map(
            caracteristica ->
              "{" +
              "\"id\": " +
              caracteristica.getId() +
              "," +
              "\"nombre\": \"" +
              caracteristica.getNombre() +
              "\"," +
              "\"valores\":" +
              this.findByEspecieYCaracteristica(especie, caracteristica)
                .stream()
                .map(
                  valor ->
                    "{" +
                    "\"id\": " +
                    valor.getId() +
                    "," +
                    "\"nombre\": \"" +
                    valor.getNombre() +
                    "\"}"
                )
                .collect(Collectors.joining(",", "[", "]")) +
              "}"
          )
          .collect(Collectors.joining(",", "[", "]")) +
        "}"
      );
    return ResponseMessage.message(
      200,
      "Valores recuperados con exito",
      data
    );
  }
}
