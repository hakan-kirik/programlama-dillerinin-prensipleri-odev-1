/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 23.03.22
* <p>
* Bu sinif dosyadan okudugu veriyi string ifadede sunar
* 
* </p>
*/
package pdpLexicalHomework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCodeFile {
	private String source;
	public ReadCodeFile(String fileName) {//dosyayi okur ve source stringine aktarir
		source = " ";
		try {
			 
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            
            while (s.hasNext()) {
                source += s.nextLine() + "\n";
            }
           
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        	//LexicalAnalyzer.error(0, 0, e.getMessage());
        }
	}
	public String getSource() {
		return this.source;
	}
}
