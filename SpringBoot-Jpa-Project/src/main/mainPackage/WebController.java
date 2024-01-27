package mainPackage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	//repository that stores Person objects
	@Autowired
	private PersonRepository repository;
	
	//returns html for the home page
	@GetMapping("/home")
	public String homePage() {
		return "home";
	}
	
	//returns html for the submit page
	//submit page contains a form for the Person object
	@GetMapping("/submit")
	public String submitPage(@ModelAttribute Person person, Model model) {
		model.addAttribute("person", person);
		return "submit";
	}
	
	//once submit is hit on the submit page, saves the Person, and shows results.html
	@PostMapping("/submit")
	public String submitAction(@ModelAttribute Person person, Model model) {
		repository.save(person);
	    return "result";
	}
	
	//returns html for the search page
	//submit page contains a form for searching by id or last name
	@GetMapping("/search")
	public String viewFilters(Model model) {
		model.addAttribute("id", new String());
		model.addAttribute("last", new String());
		return "filters";
	}
	
	//once submit is hit on the search page, builds a sql statement for a search in the db
	//@param Map<String, String> parameters map is used for "id" and "last" which correspond to inputted id and last name
	@PostMapping("/search")
	public String submitFilters(@RequestParam Map<String, String> parameters, Model model) {
		List<Person> output;
		
		String sql = "SELECT p FROM Person p WHERE ";
		int count = 0;
		boolean filters = false;
		
		//checks if an id is inputted (and not empty text)
		//if it is, adds a condition for id equality, marks the filters boolean true
		//also has a check for multiple filters so that "AND" is included in the sql
		if (parameters.get("id") != null && !parameters.get("id").replaceAll("\\s", "").equals("")) {
			if (count>0) {
				sql = sql + " AND ";
				count--;
			}
			count++;
			sql = sql + "id=" + Integer.parseInt(parameters.get("id"));
			filters = true;
		}
		
		//checks if an last name is inputted (and not empty text)
		//if it is, adds a condition for last name equality, marks the filters boolean true
		//also has a check for multiple filters so that "AND" is included in the sql
		if (parameters.get("last") != null && !parameters.get("last").replaceAll("\\s", "").equals("")) {
			if (count>0) {
				sql = sql + " AND ";
				count = count-1;
			}
			count++;
			sql = sql + "lastname='" + parameters.get("last") + "'";
			filters = true;
		}
		
		//checks bool filters to see if any filters are used
		//if used, uses the built sql statement
		//if not used, uses the findAll() method
		if (!filters) {
			output = repository.findAll();
		} else {
			output = repository.findBySQL(sql);
		}
		model.addAttribute("output", output);
		
	    return "database";
	}
	
	//returns html for the delete page
	//displays all the Person objects in the database and an input for an id
	@GetMapping("/delete")
	public String viewDelete(Model model) {
		model.addAttribute("id", new String());
		List<Person> output = repository.findAll();
		model.addAttribute("output", output);
		return "delete";
	}
	
	//once submit is hit on the delete page, builds a sql statement for a search in the db, then deletes the searched for Person
	//@param Map<String, String> parameters map is used for "id" which correspond to inputted id
	@PostMapping("/delete")
	public String deleteUpdatePage(@RequestParam Map<String, String> parameters, Model model) {
		List<Person> output = repository.findAll();
		String sql = "SELECT p FROM Person p WHERE ";
		
		//builds sql statement, finds the Person, deletes them if they exist
		if (parameters.get("id") != null && !parameters.get("id").replaceAll("\\s", "").equals("")) {
			sql = sql + "id=" + Integer.parseInt(parameters.get("id"));
			output = repository.findBySQL(sql);
			if (output.size()>0) {
				repository.delete(output.get(0));
			}
		}
		
		//shows all Person objects in the database (after deletion)
		output = repository.findAll();
		model.addAttribute("output", output);
		return "result";
	}
}
