/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 26.03.22
* <p>
* bu program kendisine verilen dosyanin icindeki operator sayisini ve operand sayisinin bilgisini ekrana yazdirir
* 
* </p>
*/
package pdpLexicalHomework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {

	public static void main(String[] args) {
		
		Lexical lexical;
		if(args.length==0) {//eger arguman girilmediyse varsayilan olarak deneme java dosyasini okur
				lexical=new Lexical("deneme.java");
		}
		else {
			 lexical=new Lexical(args[0]);
			}
		lexical.calculateOfWholeOperatorCount();//tum operatorlerin hesaplanmasi ve ekrana yazilmasi
		
		//diger tum operatorleri tek tek yazdirmak isterse enter a basmasini isteyen blok
		//yazdirmak istemezse H harfini yazmasi istenir yoksa diger neye basarsa bassin diger operatorler yazilir
		System.out.println();
		System.out.println("eger tum opertorleri tektek yazdirmak istemezseniz H yaziniz. yazdirmak isterseniz entera basin");
		 
		 char ch='\u0000';
		try {
			
			ch=(char)System.in.read();
			
		 }catch(IOException e) {
			// e.printStackTrace();
		 }
		if (ch!='\u0000' && 'h'!=Character.toLowerCase(ch)) {
			lexical.printOperators();
		}
		
	}
	
	 
}
