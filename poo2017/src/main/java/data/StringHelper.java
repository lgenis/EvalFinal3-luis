package data;

public class StringHelper {
	public static boolean isNumber(String input){
		try{
			Double.valueOf(input);
			return true;
		}catch(Exception e){
			return false;
		}
		
		
	}
	
	public static String outHtml(String title, String operacion, String nombre, int id ){
		StringBuilder out = new StringBuilder("<!doctype html public \"-//w3c//dtd html 4.0 " + 
						"transitional//en\">\n");
		
		if(id==0 || nombre==null){
			out.append(out +
			        "<html>\n" +
			           "<head><title>" + title + "</title></head>\n" +
			           "<body bgcolor = \"#f0f0f0\">\n" +
			           "<h1 align = \"center\"> No se puede realizar " + operacion + "." +
	           "</body> </html>");
		}else{
			out.append(out +
	        "<html>\n" +
	           "<head><title>" + title + "</title></head>\n" +
	           "<body bgcolor = \"#f0f0f0\">\n" +
	
	              "<h1 align = \"center\"> " + operacion + " de " + nombre
	              		+ " y Id: " + String.valueOf(id)  + "</h1>\n" +
	             
	           "</body> </html>");
		}
         return out.toString();
           
	}
	
}
