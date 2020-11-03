import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Doctor")

/* 
	Doctor class contains class variables name,address,image,speciality,experience,fees.

	Doctor class has a constructor with Arguments name,address,image,speciality,experience,fees.
	  
	Doctor class contains getters and setters for name,address,image,speciality,experience,fees.
*/

public class Doctor extends HttpServlet{
	private String id;
	private String name;
	private String address;
	private String image;
	private String speciality;
	private String experience;
	private String type;
	private double fees;
	
	public Doctor(String name, String address, String speciality, String image, String experience, double fees, String type){
		this.name=name;
		this.address=address;
		this.image=image;
		this.speciality=speciality;
		this.experience = experience;
		this.fees = fees;
		this.type = type;
	}
	
	public Doctor(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
