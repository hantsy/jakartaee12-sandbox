package com.example;

import com.example.entity.Person;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class PersonRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Person save(Person person) {
        em.persist(person);
        return person;
    }

    @Transactional
    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    @Transactional
    public List<Person> findAll() {
        return em.createQuery("select p from Person p", Person.class).getResultList();
    }
}
