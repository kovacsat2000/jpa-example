package person;

import javax.persistence.*;
import  java.time.*;
import java.util.*;
import com.github.javafaker.*;

public class Main {

    static Faker faker = new Faker();

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static Person rand_person(){
        Person person = new Person();
        person.setGender(faker.options().option(Person.Gender.class));
        person.setName(faker.name().name());
        Date date = faker.date().birthday();
        java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        person.setDob(localDate);

        Address address = new Address();
        address.setZip(faker.address().zipCode());
        address.setCountry(faker.address().country());
        address.setState(faker.address().state());
        address.setCity(faker.address().city());
        address.setStreetAddress(faker.address().streetAddress());
        person.setAddress(address);

        person.setProfession(faker.company().profession());
        person.setEmail(faker.internet().emailAddress());

        return person;
    }

    private static void creating_person(int number_of_persons){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for(int i=0; i < number_of_persons; i++)
            em.persist(rand_person());
        em.getTransaction().commit();
        em.close();
    }

    private static List<Person> list_members(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Person m", Person.class).getResultList();
    }

    public static void main(String[] args) {
        creating_person(1000);
        list_members().forEach(System.out::println);
        emf.close();
    }
}

















