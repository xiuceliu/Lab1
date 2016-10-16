import java.io.*;

public class PolynomialProcess {
	public static void main(String[] args){
		Polynomial polynomial = new Polynomial();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		String command = new String();
		while (true){
			//读取并检测, 若出错，重复读取操作
			try {  
			    command = br.readLine();         
			} catch (IOException e) {  
			    e.printStackTrace();  
			} 
			polynomial.expression(command);
			
			}
		}
	}
// this is the main class for polynomial