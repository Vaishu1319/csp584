import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
    ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		mostsold = MongoDBDataStoreUtilities.mostsoldProviders();
		mostsoldzip = MongoDBDataStoreUtilities.mostsoldZip();
		bestrated = MongoDBDataStoreUtilities.topProviders();

		

		String name = "Trending";
		

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		//pw.print("<h2 class='title meta' style='font-size: 32px;color: #8f0419 !important;''>Trending</h2>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 32px;color: #8f0419 !important;'>Trending</a></h2></div></div>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 20px;'><strong>Best Products</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr2 = bestrated.iterator();
        while(itr2.hasNext()) {
        Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr>");
		pw.print("<td border: 1px >");
		pw.print(best.getProductname());
		pw.print("</td>");
		pw.print("<td border: 1px style='width:25%;'>");
		
		String cutString = "";
		int length = best.getRating().length();
		if (length <= 3){
			cutString = best.getRating().substring(0, 3);
		}
		else{
			cutString = best.getRating().substring(0, 4);
		}
		pw.print(cutString);
		pw.print("</td>");
		pw.print("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 20px;'><strong>Most Sold Products by Zipcode</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr1 = mostsoldzip.iterator();
        while(itr1.hasNext()) {
        Mostsoldzip mostzip = (Mostsoldzip)itr1.next();
 		pw.print("<tr>");
		pw.println("<td border: 1px >");
		
		pw.println(mostzip.getZipcode());
		pw.println("</td>");
		pw.println("<td border: 1px style='width:25%;'>");
		pw.println(mostzip.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 20px;'><strong>Most Sold Products</strong></a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		
        Iterator itr = mostsold.iterator();
        while(itr.hasNext()) {
        Mostsold most = (Mostsold)itr.next();
 		pw.println("<tr>");
		pw.println("<td border: 1px >");
		pw.println(most.getProductname());
		pw.println("</td>");
		pw.println("<td border: 1px style='width:25%;'>");
		pw.println(most.getCount());
		pw.println("</td>");
		pw.println("</tr>");
        }
        pw.print("<tr><td></td><td></td></tr>");
		pw.print("</table></div></div></div>");
		
	//	pw.print("</table></div></div></div>");	
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
