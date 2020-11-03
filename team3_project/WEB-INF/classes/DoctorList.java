import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DoctorList")

public class DoctorList extends HttpServlet {

	/* Doctors Page Displays all the Doctors and their Information in Doctor Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Doctor> alldoctors = new HashMap<String,Doctor> ();

		try{
			alldoctors = MySqlDataStoreUtilities.getDoctors();
		}
		catch(Exception e)
		{

		}

		/* Checks the Doctors type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String DoctorName = request.getParameter("name");
		HashMap<String, Doctor> hm = new HashMap<String, Doctor>();
		
		if(DoctorName==null)
		{
			hm.putAll(alldoctors);
			name = "";
		}
		/*else
		{
		  if(ProductName.equals("htc"))
		  {
			for(Map.Entry<String,Doctor> entry : alldoctors.entrySet())
				{
				if(entry.getValue().getRetailer().equals("HTC"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "HTC";
		  }
		  else if(ProductName.equals("oculus"))
		  {
			for(Map.Entry<String,Doctor> entry : alldoctors.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Oculus"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Oculus";
		  }
		  else if(ProductName.equals("samsung"))
		  {
			for(Map.Entry<String,Doctor> entry : alldoctors.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Samsung"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Samsung";
		  }
		  else if(ProductName.equals("homido"))
		  {
			for(Map.Entry<String,Doctor> entry : alldoctors.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Homido"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Homido";
		  }
		  else if(ProductName.equals("sony"))
		  {
			for(Map.Entry<String,Doctor> entry : alldoctors.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Sony"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Sony";
		  }
		}*/

		/* Header, Left Navigation Bar are Printed.

		All the Doctors and Doctors information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>"+name+" Doctor Lists</a>");
		pw.print("</h2><div class='entry'>");
		for(Map.Entry<String, Doctor> entry : hm.entrySet()){
			Doctor doctor = entry.getValue();
			String type = doctor.getType();
			if (type.equalsIgnoreCase("Doctor")) {
				pw.print("</br>");
				pw.print("<div class='info_card'><div class='card_image'><div class='flex'>");
				pw.print("<img src='images/doctor/"+doctor.getImage()+"' alt='' style='height:140px;'/></div>");
				pw.print("<div class='card_image1'>");
				String dname = doctor.getName().substring(0, 1).toUpperCase() + doctor.getName().substring(1);
				pw.print("<span><h3 style='text-align:left !important'>Dr. " + dname + "</h3></span>");
				pw.print("<span><h4>"+ doctor.getSpeciality() + "</h4></span>");
				pw.print("<span><h4>"+ doctor.getExperience() + " years experience overall</h4></span>");
				pw.print("<span><h4>"+ doctor.getAddress() + "</h4></span>");
				pw.print("<span><h4>$"+ doctor.getFees() + " Consultation fee at clinic</h4></span></div></div></div>");
				/*pw.print("<form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
						"<input type='hidden' name='type' value='Virtual Reality'>"+
						"<input type='hidden' name='maker' value='"+name+"'>"+
						"<input type='hidden' name='access' value=''>"+
						"<input type='submit' value='View Review' class='btnreview'></form></li>");
				pw.print("</br></br>");
				pw.print("<ul><li><form method='post' action='Cart'>" +
						"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
						"<input type='hidden' name='type' value='doctors'>"+
						"<input type='hidden' name='maker' value='"+name+"'>"+
						"<input type='hidden' name='access' value=''>"+
						"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
				pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
						"<input type='hidden' name='type' value='Virtual Reality'>"+
						"<input type='hidden' name='maker' value='"+name+"'>"+
						"<input type='hidden' name='price' value='"+doctor.getPrice()+"'>"+
						"<input type='hidden' name='access' value=''>"+
						"<input type='submit' value='Write Review' class='btnreview'></form></li></ul>");*/
			}
			
		}		
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
