package com.example;

import com.example.PersonRepository;
import com.example.entity.Person;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ArquillianExtension.class)
public class PersonRepositoryIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "persistence.war")
                .addClasses(Person.class, PersonRepository.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private PersonRepository repository;

    @Test
    public void testSaveAndFind() {
        Person saved = repository.save(new Person("Jakarta EE 12"));
        assertNotNull(saved.getId(), "Person should be assigned an id on persist");

        Person found = repository.findById(saved.getId());
        assertNotNull(found, "Person should be findable by id");
        assertEquals("Jakarta EE 12", found.getName());

        assertFalse(repository.findAll().isEmpty(), "Person should be listed in findAll");
    }
}
