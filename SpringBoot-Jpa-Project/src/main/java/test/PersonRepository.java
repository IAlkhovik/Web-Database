package test;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
@Repository
public class PersonRepository {
 
	//keeps track of the Person objects that are in the MySQL db
    @Autowired private EntityManager entityManager;
 
    //saves a Person to the MySQL db
    //@param Person person to be saved
    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }
    
    //removes a Person from the MySQL db
    //@param Person person to be removed
    @Transactional
    public void delete(Person person) {
    	entityManager.remove(person);
    }
    
    //displays all Person objects in the db
    public List<Person> findAll(){
    	String jpql = "SELECT p FROM Person p";
    	TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
    	return query.getResultList();
    }
    
    /*public List<Person> findById(Integer id) {
    	String jpql = "SELECT p FROM Person p WHERE id=" + id;
    	TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
    	return query.getResultList();
    }
    
    public List<Person> findByLastName(String lastName) {
    	String jpql = "SELECT p FROM Person p WHERE lastName='" + lastName + "'";
    	TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
    	return query.getResultList();
    }
    
    public List<Person> findByIdAndName(Integer id, String lastName){
    	String jpql = "SELECT p FROM Person p WHERE id=" + id + "AND lastName='" + lastName + "'";
    	TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
    	return query.getResultList();
    }*/
    
    //displays Person objects based on the inputted sql statement
    //@param sql sql statement
    public List<Person> findBySQL(String sql){
    	TypedQuery<Person> query = entityManager.createQuery(sql, Person.class);
    	return query.getResultList();
    }
}