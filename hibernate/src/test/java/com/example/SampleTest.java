package com.example;

import com.example.book.Author;
import com.example.book.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SampleTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("hibernateSandbox");
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void testPersistBook() {
        em.getTransaction().begin();
        Author author = new Author("Hantsy Bai");
        em.persist(author);
        Book book = new Book("Jakarta Persistence 4.0", "978-0-0000-0000-0", author);
        em.persist(book);
        em.getTransaction().commit();

        em.getTransaction().begin();
        List<Book> books = em.createQuery("select b from Book b", Book.class).getResultList();
        assertEquals(1, books.size());

        Book loaded = books.get(0);
        assertNotNull(loaded.getId());
        assertEquals("Jakarta Persistence 4.0", loaded.getTitle());
        assertEquals("Hantsy Bai", loaded.getAuthor().getName());
        em.getTransaction().commit();
    }
}
