/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Controles.exceptions.NonexistentEntityException;
import Controles.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Palabras;
import Modelo.Tipos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author manel
 */
public class TiposJpaController implements Serializable {

    public TiposJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipos tipos) throws RollbackFailureException, Exception {
        if (tipos.getPalabrasList() == null) {
            tipos.setPalabrasList(new ArrayList<Palabras>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Palabras> attachedPalabrasList = new ArrayList<Palabras>();
            for (Palabras palabrasListPalabrasToAttach : tipos.getPalabrasList()) {
                palabrasListPalabrasToAttach = em.getReference(palabrasListPalabrasToAttach.getClass(), palabrasListPalabrasToAttach.getIdpalabra());
                attachedPalabrasList.add(palabrasListPalabrasToAttach);
            }
            tipos.setPalabrasList(attachedPalabrasList);
            em.persist(tipos);
            for (Palabras palabrasListPalabras : tipos.getPalabrasList()) {
                Tipos oldTipoOfPalabrasListPalabras = palabrasListPalabras.getTipo();
                palabrasListPalabras.setTipo(tipos);
                palabrasListPalabras = em.merge(palabrasListPalabras);
                if (oldTipoOfPalabrasListPalabras != null) {
                    oldTipoOfPalabrasListPalabras.getPalabrasList().remove(palabrasListPalabras);
                    oldTipoOfPalabrasListPalabras = em.merge(oldTipoOfPalabrasListPalabras);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipos tipos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipos persistentTipos = em.find(Tipos.class, tipos.getIdtipos());
            List<Palabras> palabrasListOld = persistentTipos.getPalabrasList();
            List<Palabras> palabrasListNew = tipos.getPalabrasList();
            List<Palabras> attachedPalabrasListNew = new ArrayList<Palabras>();
            for (Palabras palabrasListNewPalabrasToAttach : palabrasListNew) {
                palabrasListNewPalabrasToAttach = em.getReference(palabrasListNewPalabrasToAttach.getClass(), palabrasListNewPalabrasToAttach.getIdpalabra());
                attachedPalabrasListNew.add(palabrasListNewPalabrasToAttach);
            }
            palabrasListNew = attachedPalabrasListNew;
            tipos.setPalabrasList(palabrasListNew);
            tipos = em.merge(tipos);
            for (Palabras palabrasListOldPalabras : palabrasListOld) {
                if (!palabrasListNew.contains(palabrasListOldPalabras)) {
                    palabrasListOldPalabras.setTipo(null);
                    palabrasListOldPalabras = em.merge(palabrasListOldPalabras);
                }
            }
            for (Palabras palabrasListNewPalabras : palabrasListNew) {
                if (!palabrasListOld.contains(palabrasListNewPalabras)) {
                    Tipos oldTipoOfPalabrasListNewPalabras = palabrasListNewPalabras.getTipo();
                    palabrasListNewPalabras.setTipo(tipos);
                    palabrasListNewPalabras = em.merge(palabrasListNewPalabras);
                    if (oldTipoOfPalabrasListNewPalabras != null && !oldTipoOfPalabrasListNewPalabras.equals(tipos)) {
                        oldTipoOfPalabrasListNewPalabras.getPalabrasList().remove(palabrasListNewPalabras);
                        oldTipoOfPalabrasListNewPalabras = em.merge(oldTipoOfPalabrasListNewPalabras);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipos.getIdtipos();
                if (findTipos(id) == null) {
                    throw new NonexistentEntityException("The tipos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipos tipos;
            try {
                tipos = em.getReference(Tipos.class, id);
                tipos.getIdtipos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipos with id " + id + " no longer exists.", enfe);
            }
            List<Palabras> palabrasList = tipos.getPalabrasList();
            for (Palabras palabrasListPalabras : palabrasList) {
                palabrasListPalabras.setTipo(null);
                palabrasListPalabras = em.merge(palabrasListPalabras);
            }
            em.remove(tipos);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipos> findTiposEntities() {
        return findTiposEntities(true, -1, -1);
    }

    public List<Tipos> findTiposEntities(int maxResults, int firstResult) {
        return findTiposEntities(false, maxResults, firstResult);
    }

    private List<Tipos> findTiposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tipos findTipos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipos> rt = cq.from(Tipos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
