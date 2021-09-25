package com.eryalus.emptybot.persistence.repositories;

import com.eryalus.emptybot.persistence.entities.DefaultEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.TransactionException;





public abstract class Repository<T> {
    protected EntityManager em;
    final Class<T> type;
    
    public Repository(Class<T> type, EntityManager em) {
        this.em = em;
        this.type = type;
    }

    public T findById(Long id){
        return em.find(type, id);
    }

    public T save(T entity){         
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            if (((DefaultEntity) entity).getId() == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new TransactionException(ex.getMessage());
        }
        return entity;
    }

    public void delete(T entity){
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            if ( em.contains(entity)){
                em.remove(entity);
            }else{
                em.merge(entity);
            }
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new TransactionException(ex.getMessage());
        }
    }

}
