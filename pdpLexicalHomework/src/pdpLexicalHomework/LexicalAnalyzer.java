/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 23.03.22
* <p>
* Bu sinif kendisine verilen string halindeki kodu inceleyerek kod parcalarini etiketlendirir(tokenini belirler)
* 
* </p>
*/
package pdpLexicalHomework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LexicalAnalyzer {
    private int line;
    private int pos;
    private int position;
    private char chr;
    private String s;
 
   private Map<String, TokenType> keywords = new HashMap<>();//keywordlerin tutulacagi hasmap
    
    LexicalAnalyzer(String source) {
        this.line = 1;
        this.pos = 0;
        this.position = 0;
        this.s = source;
        this.chr = this.s.charAt(0);//stringin ilk degerinin chr degiskenine tanimlanmasi
        //keywordlerin tanimlanmasi
        this.keywords.put("if", TokenType.Keyword);
        this.keywords.put("else", TokenType.Keyword);
        this.keywords.put("System", TokenType.Keyword);
        this.keywords.put("out", TokenType.Keyword);
        this.keywords.put("println", TokenType.Keyword);
        this.keywords.put("print", TokenType.Keyword);
        this.keywords.put("while", TokenType.Keyword);
        this.keywords.put("for", TokenType.Keyword);
        this.keywords.put("public", TokenType.Keyword);
        this.keywords.put("class", TokenType.Keyword);
        this.keywords.put("static", TokenType.Keyword);
        this.keywords.put("package", TokenType.Keyword);
        this.keywords.put("import", TokenType.Keyword); 
        this.keywords.put("return", TokenType.Keyword);
     
     
 
    }


   
 
  
    static void error(int line, int pos, String msg) {//olasi yazim hatalarinda sonsuz dongude kalmamak ve kullaniciyi nerede hata olduguna dair uyarma mesaji
        if (line > 0 && pos > 0) {
            System.out.printf("%s satir: %d, pozisyon: %d\n", msg, line, pos);
        } else {
            System.out.println(msg);
        }
        System.exit(1);
    }
 
  
   private Token LookfollowChar(char expect, TokenType ifyes, TokenType ifno, int line, int pos) {//eger beklenen deger ise olasi gereken tipi dondurur degilse diger olasigi dondurur
        if (getNextChar() == expect) {
            getNextChar();
            return new Token(ifyes, "", line, pos);
        }
      
        return new Token(ifno, "", line, pos);
    }
  //burasida yukarisi ile ayni buradaki tek fark iki tane beklenen var ve 3 token tipi var
   private  Token LookfollowChar(char expect,char expect2 ,TokenType if1, TokenType if2,TokenType ifno, int line, int pos) {
    	char option=getNextChar();
    	if (option == expect) {
            getNextChar();
            return new Token(if1, "", line, pos);
        }else if(option==expect2)
        {
        	getNextChar();
            return new Token(if2, "", line, pos);
        }
       
        return new Token(ifno, "", line, pos);
    }
     
   private Token charExp(int line, int pos) {
        char c = getNextChar(); //acilan tirnagi gecme
        int n = (int)c;
        if (c == '\'') {//eger ikinci karakter ' ise bos karakter iceriyor diye hata verir
            error(line, pos, "bos karakter iceriyor");
        } else if (c == '\\') {//kacis karakteri ise bir sonraki degere gecer
            c = getNextChar();
            if (c == 'n') {//newline kacis ifadesi ise 10 degeri atanir ve token belirlenmistir
                n = 10;
            } else if (c == '\\') {// /kacis ifadesi ise degeri atanir ve token belirlenir
                n = '\\';
            }else if(c=='u') {//unicod kacis ifadesi ise diger 4 karakter okunur ve token belirlenir
            	int lastPos=this.position;
            	while(true) {
            		char nextC=getNextChar();
            		if(nextC=='\'' && ((this.position-lastPos)!=5))
            		{
            			error(line,pos,"unicode formatina uygun degil");//eger 4 karakterden fazla ise  hata verir
            			
            		}else if(nextC=='\'')//dosya sonuna gelinmisse ve "'" ifadesi bulunamadiysa hata verir
            		{
            			getNextChar();
            			return new Token(TokenType.Uchar,"",line,pos);
            		}
            	}	
            }
            
        }
        if (getNextChar() != '\'') {//gelen ifade ' tirnak degilse coklu karakter iceriyordur ve hata verir
            error(line, pos, "coklu karakter iceriyor");
        }
        getNextChar();
        return new Token(TokenType.Char, "" + n, line, pos);
    }
    
   private Token stringExp(char start, int line, int pos) {//string ifadenin tokenini belirler
        String result = "";
        char ch=getNextChar();
        while (ch != start) {
            if (this.chr == '\u0000') {//dosya sonuna gelindiyse hata verir
                error(line, pos, "string kapsam disi");
            }
            if (this.chr == '\n') {//yeni satira gecildiyse hata verir
                error(line, pos, "string kapsam disi");
            }
            
            result += this.chr;
            if(ch=='\\') {//eger kacis ifadesi gorurse direkt o ifadeyi gecer 
            	ch=getNextChar();
            	result+=this.chr;
            	
            }
            ch=getNextChar();
        }
        
        getNextChar();
        return new Token(TokenType.String, result, line, pos);
    }
    
   private Token commentsOrDiv(int line, int pos) {//yorum satilarini temizler yada bolum operatorlerinin tokenlarini belirler
    	char nchr=getNextChar();
        if (nchr != '*') {//eger sonraki karakter * degilse bu ifade yorum satiri degildir
        	if(nchr=='=') {//sonraki karakter = ise /= ifadesi token belirlenir
        		getNextChar();
        		return new Token(TokenType.Op_divAssign,"",line,pos);
        	}
        	if(nchr!='/')//eger gelen ifade / degilse bolum operatorudur
            return new Token(TokenType.Op_divide, "", line, pos);
        }
        
        if(nchr=='/')//ikinci gelen ifade / ise tekli yorum satiridir 
        while(true) {
        	
        	  if(this.chr!='\n') {//satir sonuna gelindi ise sonraki tokeni dondurur
        		 getNextChar();
        	 }else {
        		 getNextChar();
        		 return getToken();
        	 }
        }
       getNextChar();
        while (true) { //yukaridaki kontrolleri gecti ise coklu yorum satiridir
            if (this.chr == '\u0000') {//dosya sonuna gelindi ise hata verir
                error(line, pos, "hatali yorum satiri");
            }
            else if (this.chr == '*') {//yorum sayiri kapanma ifadesi gorurse cikar ve ileriki token degerini dondurur
                if (getNextChar() == '/') {
                    getNextChar();
                    return getToken();
                }
            }
            else {//yorum satiri kapanma ifadesi gorenen kadar ilerler
                getNextChar();
            }
        }
    }
    
    
   private  Token integerOrIdentifier(int line, int pos) {//sayi yada niteleyici olup olmadigina bakar
        boolean is_number = true;
        String text = "";
 
        while (Character.isAlphabetic(this.chr) || Character.isDigit(this.chr) || this.chr == '_' ||this.chr=='$') {//texti belirtilen kosullara gore ekler
            text += this.chr;
            if (!Character.isDigit(this.chr)) {///gelen deger sayi degil ise 
                is_number = false;
            }
            getNextChar();
        }
 
        if (text.equals("")) {//while hic girilmemis ve getToken methodunda tanimlanmadi  ise tanimsiz bir karakter girilmistir hata verir
            error(line, pos, String.format("integerOrIdentifier: tanimsiz karakter (%d) %c", (int)this.chr, this.chr));
        }
 
        if (Character.isDigit(text.charAt(0))) {//ilk karakter sayi ise ve sonraki deger harf ise tanimsiz bir ifadedir ve hata verir
            if (!is_number) {//
                error(line, pos, String.format("tanimsiz sayi: %s", text));
            }
            return new Token(TokenType.Integer, text, line, pos);
        }
 
        if (this.keywords.containsKey(text)) {//eger keyword map inin icinde tanimli ise keyword tokeni belirlenir
            return new Token(this.keywords.get(text), text, line, pos);
        }
        return new Token(TokenType.Identifier, text, line, pos);//yukaridakilerin hicbiri degilse identifierdir
    }
    
    private boolean findGeneric() {//generik ifade var ise olari bulur eger ic ice generic var ise recursive olarak bulur 
    	int poss=this.position;//baslangic degerlerinin tutulmasi
    	int pos=this.pos;
    	int line=this.line;
    	getNextChar();
    	
    	Token p=getToken();//sonraki tokeni cagirma
    	while(true) {
    		
    		if(!(p.tokentype==TokenType.Identifier ||p.tokentype==TokenType.Comma||p.tokentype==TokenType.Op_greater
    				||p.tokentype==TokenType.generic||p.tokentype==TokenType.LeftBracket||p.tokentype==TokenType.RightBracket)) //kosulunu sagliyor ise generik degildir 
    		{
    			this.pos=pos;//ve baslangic degerleri geri atanir ve findGeneric ten cikilir
    			this.line=line;
    			this.position=poss;
    			break ;//burada false donecek
    		}
    		if(p.tokentype==TokenType.Op_greater) {//eger kosul saglanirsa generictir ve true dondurulur
    			
    			return true;// bu true dondurur
    			
    		}
    		p=getToken();
    	}
    	
    	
    return false;
    	
    }
    
    private  Token getToken() {
        int line, pos;
        while (Character.isWhitespace(this.chr)) {//bosluk karakterlerini gecer
            getNextChar();
        }
        line = this.line;//satir bilgisi
        pos = this.pos;//pozisyon bilgisi
 
        switch (this.chr) {//gelen char ifadesine gore methodlara yonlerdirir ve tokenlerini belirler
            case '\u0000': return new Token(TokenType.End_of_input, "", this.line, this.pos);//null a gelindiyse dosya sonudur
            case '/': return commentsOrDiv(line, pos);//yorum satiri ise gecer yorum bitikten sonra gelen ifade tokeni dondurur bolum yada bol ata isareti ise token geri dondurur.
            case '\'': return charExp(line, pos);// ' ifadesi geldiginde duzgu
            case '<': 
            	boolean gn=findGeneric();
            	if(!gn) {
            		return LookfollowChar('=', TokenType.Op_lessequal, TokenType.Op_less, line, pos);
            	}else {
            		return new Token(TokenType.generic,"",this.line,this.pos);
            	}
            case '>': return LookfollowChar('=', TokenType.Op_greaterequal, TokenType.Op_greater, line, pos);
            case '=': return LookfollowChar('=', TokenType.Op_equal, TokenType.Op_assign, line, pos);
            case '!': return LookfollowChar('=', TokenType.Op_notequal, TokenType.Op_not, line, pos);
            case '&': return LookfollowChar('&','=' ,TokenType.Op_and, TokenType.Op_andAssign,TokenType.Op_bitwiseAnd, line, pos);
            case '|': return LookfollowChar('|', '=',TokenType.Op_or, TokenType.Op_orAssign,TokenType.Op_bitwiseOr, line, pos);
            case '"': return stringExp(this.chr, line, pos);
            case '{': getNextChar(); return new Token(TokenType.LeftBrace, "", line, pos);
            case '}': getNextChar(); return new Token(TokenType.RightBrace, "", line, pos);
            case '(': getNextChar(); return new Token(TokenType.LeftParen, "", line, pos);
            case ')': getNextChar(); return new Token(TokenType.RightParen, "", line, pos);
            case '+': return LookfollowChar('=','+' ,TokenType.Op_plusAssign,TokenType.Op_plusplus, TokenType.Op_add, line, pos);
            case '-': return LookfollowChar('=','-' ,TokenType.Op_subAssign,TokenType.Op_subsub, TokenType.Op_subtract, line, pos);
            case '*': return LookfollowChar('=', TokenType.Op_mulAssign, TokenType.Op_multiply, line, pos);
            case '%': return LookfollowChar('=', TokenType.Op_modAssign, TokenType.Op_mod, line, pos);
            case ';': getNextChar(); return new Token(TokenType.Semicolon, "", line, pos);
            case ',': getNextChar(); return new Token(TokenType.Comma, "", line, pos);
            case '.': getNextChar(); return new Token(TokenType.Dot,"", line, pos);
            case '^': return LookfollowChar('=', TokenType.Op_XORAssign, TokenType.Op_bitwiseXOR, line, pos);
            case '[': getNextChar(); return new Token(TokenType.LeftBracket, "", line, pos);
            case ']': getNextChar(); return new Token(TokenType.RightBracket, "", line, pos);
            case '@': getNextChar(); return new Token(TokenType.Keyword,"",line ,pos);
            case '~': getNextChar(); return new Token(TokenType.Keyword,"",line,pos);
            default: return integerOrIdentifier(line, pos);
        }
    }
 
    private  char getNextChar() {//sonraki char ifadeyi getiri ve pozisyon bilgilerini gunceller
        this.pos++;
        this.position++;
        if (this.position >= this.s.length()) {
            this.chr = '\u0000';
            return this.chr;
        }
        this.chr = this.s.charAt(this.position);
        if (this.chr == '\n') {
            this.line++;
            this.pos = 0;
        }
        return this.chr;
    }
 

    
    public ArrayList<Token> getTokensArr() {//tokenlari arralist olarak dondurur
    	ArrayList<Token> tokens=new ArrayList<Token>();
        Token t;
        while ((t = getToken()).tokentype != TokenType.End_of_input) {
           
            tokens.add(t);
        }
        
        return tokens;
       
    }
    
}
