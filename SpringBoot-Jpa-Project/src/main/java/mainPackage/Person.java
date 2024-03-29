package mainPackage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Person object stored in the database
@Entity
public class Person {
	//id generated by db
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	//Person's first name
	@Column(name="firstname")
	private String firstName;
	
	//Person's last name
	@Column(name="lastname")
	private String lastName;
	
	//Person's age
	@Column(name="age")
	private int age;
	
	//Person's occupation
	@Column(name="occupation")
	private String occupation;
	
	//Empty constructor
	public Person() {}
	
	//Constructor with all parameters as inputs
	public Person(String firstName, String lastName, int age, String occupation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.occupation = occupation;
	}
	
	//Method printing the Person object
	@Override
	public String toString() {
		return String.format("Person[id = %d, First Name = '%s', Last Name = '%s', Age = %d, Occupation = '%s']",
				this.id, this.firstName, this.lastName, this.age, this.occupation);
	}
	
	//Getters and Setters for all fields
	public Integer getId() {
		return this.id;
	}
	public Integer setId(Integer id) {
		return this.id = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	public String setFirstName(String firstName) {
		return this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	public String setLastName(String lastName) {
		return this.lastName = lastName;
	}
	
	public int getAge() {
		return this.age;
	}
	public int setAge(int age) {
		return this.age = age;
	}
	
	public String getOccupation() {
		return this.occupation;
	}
	public String setOccupation(String occupation) {
		return this.occupation = occupation;
	}
}