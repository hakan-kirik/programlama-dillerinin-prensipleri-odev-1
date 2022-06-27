/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 23.03.22
* <p>
* Bu sinif lexical analizde yapilacak analiz degerlerinin tutulmasi icin olusturulmustur
* tokentipi,value(degeri), satir bilgisi ve pozisyon bilgisini tutar
* </p>
*/
package pdpLexicalHomework;

public class Token {
    public TokenType tokentype;
    public String value;
    public int line;
    public int pos;
    Token(TokenType token, String value, int line, int pos) {//operatorlerin tiplerinin degerlerinin hangi satirda hangi pozisyonda oldugu bilgisini tutan token class i
        this.tokentype = token; this.value = value; this.line = line; this.pos = pos;
    }
    @Override
    public String toString() {//token i ekrana yazdirmak istedigimizde ekrana yazdirdigi format
        String result = String.format("%5d  %5d %-15s", this.line, this.pos, this.tokentype);
        switch (this.tokentype) {//token tipine gore ekrana farkli bir bicimde yazdirir
            case Integer:
                result += String.format("  %4s", value);
                break;
            case Identifier:
                result += String.format(" %s", value);
                break;
            case String:
                result += String.format(" \"%s\"", value);
                break;
            case Keyword:
            	 result += String.format(" %s", value);
                 break;
        }
        return result;
    }
}
