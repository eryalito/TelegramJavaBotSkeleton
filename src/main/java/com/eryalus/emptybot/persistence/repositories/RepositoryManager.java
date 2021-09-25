package com.eryalus.emptybot.persistence.repositories;

import com.eryalus.emptybot.persistence.util.HibernateUtil;

public class RepositoryManager {
    public static PersonRepository getPersonRepository(){
        return new PersonRepository(HibernateUtil.getSessionFactory().openSession().getEntityManagerFactory().createEntityManager());
    }
}
