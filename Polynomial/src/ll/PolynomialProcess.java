package ll;
import java.io.*;
public class PolynomialProcess {
	public void debug() {
		System.out.println("________");
	}
	public static void main(String[] args){
		Polynomial polynomial = new Polynomial();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		String command = new String();
		//debug();
		while (true){
			try {  
			    command = br.readLine();         
			} catch (IOException e) {  
			    e.printStackTrace();  
			} 
			polynomial.expression(command);
			
			}
		}
	}