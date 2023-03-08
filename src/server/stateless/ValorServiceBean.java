package stateless;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
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

  Logger logger = Logger.getLogger(getClass().getName());

  public EntityManager getEntityManager() {
    return em;
  }

  @Override
  public Valor create(Valor valor) {
    valor.setId(this.getMaxId() + 1);

    em.persist(valor);
    return valor;
  }

  @Override
  public void darBaja(Valor valor) {
    em.merge(valor);
  }

  @Override
  public Valor find(Valor valor) {
    try {
      return em
          .createQuery(
              "select valor " +
                  "from Valor valor " +
                  "where valor.nombre = :valor " +
                  "and valor.especie.nombre = :especie " +
                  "and valor.caracteristica.nombre = :caracteristica",
              Valor.class)
          .setParameter("valor", valor.getNombre())
          .setParameter("especie", valor.getEspecie().getNombre())
          .setParameter("caracteristica", valor.getCaracteristica().getNombre())
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
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
      Caracteristica caracteristica) {
    try {
      return em
          .createQuery(
              "select valor " +
                  "from Valor valor " +
                  "where valor.especie.nombre = :especie " +
                  "and valor.caracteristica.nombre = :caracteristica",
              Valor.class)
          .setParameter("especie", especie.getNombre())
          .setParameter("caracteristica", caracteristica.getNombre())
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Valor> findByEspecieYCaracteristicaEnabled(
      Especie especie,
      Caracteristica caracteristica) {
    try {
      return em
          .createQuery(
              "select valor " +
                  "from Valor valor " +
                  "where valor.especie.nombre = :especie " +
                  "and valor.caracteristica.nombre = :caracteristica " +
                  "and valor.deshabilitado = FALSE",
              Valor.class)
          .setParameter("especie", especie.getNombre())
          .setParameter("caracteristica", caracteristica.getNombre())
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<Valor> findEnabled(
      Especie especie,
      Caracteristica caracteristica) {
    try {
      return em
          .createQuery(
              "select valor " +
                  "from Valor valor " +
                  "where valor.especie.nombre = :especie " +
                  "and valor.caracteristica.nombre = :caracteristica " +
                  "and valor.deshabilitado = FALSE " +
                  "order by valor.deshabilitado",
              Valor.class)
          .setParameter("especie", especie.getNombre())
          .setParameter("caracteristica", caracteristica.getNombre())
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public String findCaracteristicasConValores(Especie especie) {
    Collection<Caracteristica> caracteristicas = this.serviceCaracteristica.findByEspecie(especie);
    String data = (caracteristicas
        .stream()
        .map(
            caracteristica -> "{" +
                "\"id\": " +
                caracteristica.getId() +
                "," +
                "\"nombre\": \"" +
                caracteristica.getNombre() +
                "\"," +
                "\"valores\":" +
                this.findByEspecieYCaracteristicaEnabled(especie, caracteristica)
                    .stream()
                    .map(
                        valor -> "{" +
                            "\"id\": " +
                            valor.getId() +
                            "," +
                            "\"nombre\": \"" +
                            valor.getNombre() +
                            "\"}")
                    .collect(Collectors.joining(",", "[", "]"))
                +
                "}")
        .collect(Collectors.joining(",", "[", "]")));
    return ResponseMessage.message(200, "Valores recuperados con exito", data);
  }

  public Valor findByCaracteristicaEspecieValor(
      Caracteristica caracteristica,
      Especie especie,
      Valor valor) {
    logger.info("especie: " + especie.getNombre().toLowerCase());
    logger.info("caracteristica: " + caracteristica.getNombre().toLowerCase());
    logger.info("valor: " + valor.getNombre().toLowerCase());

    try {
      return getEntityManager()
          .createNamedQuery("Valor.findByCaracteristicaEspecieValor", Valor.class)
          .setParameter("caracteristica_nombre", caracteristica.getNombre())
          .setParameter("especie_nombre", especie.getNombre())
          .setParameter("valor_nombre", valor.getNombre())
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<Valor> findValoresByListAndEspecie(
      List<Valor> valores,
      Especie especie) {
    List<Valor> listaNuevaValores = new ArrayList<Valor>();
    Valor valorEncontrado;
    for (Valor valor : valores) {
      logger.info("caracteristica: "+valor.getCaracteristica());
      valorEncontrado = this.findByCaracteristicaEspecieValor(
          valor.getCaracteristica(),
          especie,
          valor);
      logger.info("Valor encontrado: " + valorEncontrado);
      if (valorEncontrado != null) {
        valorEncontrado.getMascotas().clear();
        listaNuevaValores.add(valorEncontrado);
      }
    }

    return listaNuevaValores;
  }

  public long getMaxId() {
    try {
      return getEntityManager()
          .createNamedQuery("Valor.getMaxId", Long.class)
          .getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }
}
