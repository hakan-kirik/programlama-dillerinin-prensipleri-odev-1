/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* @since 25.03.22
* <p>
* Bu sinif Lexical_analizden aldigi token bilgilerine gore operatorleri sayar
* 
* </p>
*/
package pdpLexicalHomework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class OperatorCounter {
	private ArrayList<Token> tokens=new ArrayList<Token>();
	
	private int arr_size;
	private int pos;
	private int position;
	private Token tkn;
	private TokenType[] operatorTypes;
	private HashMap<TokenType, Integer> countsOfOperators;
	OperatorCounter(String fileName){
		
		pos=0;
		position=0;
		countsOfOperators=new HashMap<>();
		//bakilacak olan tum operatorlerin token tipleri dizisi
		operatorTypes=new TokenType[] {TokenType.Op_multiply,TokenType.Op_divide,TokenType.Op_mod, 
				TokenType.Op_add,TokenType.Op_subtract,	 TokenType.Op_less,TokenType.Op_lessequal,TokenType.Op_greater,
				TokenType.Op_greaterequal,TokenType.Op_equal, TokenType.Op_notequal,TokenType.Op_assign,
				TokenType.Op_and, TokenType.Op_or,	TokenType.Op_plusAssign,TokenType.Op_subAssign,
				TokenType.Op_mulAssign,TokenType.Op_bitwiseAnd,TokenType.Op_bitwiseOr,
				TokenType.Op_modAssign,TokenType.Op_andAssign,TokenType.Op_orAssign,
			    TokenType.Op_divAssign,TokenType.Op_XORAssign, TokenType.Op_bitwiseXOR,
			    TokenType.Op_subsub,TokenType.Op_plusplus,TokenType.Op_not};
		
		
		ReadCodeFile fileText=new ReadCodeFile(fileName);//dosyayi okur ve texti alir
		String source=fileText.getSource();
		LexicalAnalyzer l = new LexicalAnalyzer(source);//lexical analiz ile tokenlari belirlenir
         
         tokens= l.getTokensArr();//tokenler diziye aktarilir
		arr_size=tokens.size();
		
		if(arr_size==0)//dizi bos ise dosay bostur ve hata verir
			LexicalAnalyzer.error(0, 0, String.format("%s : Bos dosya hatasi", this.getClass().getName()));
			
		
		
		
	}
	
	
	public  Map<TokenType, Integer> countOperators(){//tum operatorlerin sayisini bulur ve tipi ile sayisini hasmap olarak dondurur
		
		//HashMap<TokenType, Integer> countsOfOperators=new HashMap<>();
		
	
		for(TokenType t:operatorTypes)//operatorlerin tek tek sayilarini bulur
		{
			switch(t){
				case Op_add:
					countsOfOperators.put(TokenType.Op_singleAdd, findOperatorCounts(t, true));//isaret operatoru olan + nin sayisini bulur
					countsOfOperators.put(t, findOperatorCounts(t, false));//ikili operator olan + nin sayisini bulur
					break;
				case Op_subtract:
					countsOfOperators.put(TokenType.Op_singleSub, findOperatorCounts(t, true));
					countsOfOperators.put(t, findOperatorCounts(t, false));
					break;
				case Op_plusplus:
					countsOfOperators.put(TokenType.Op_plusplus, findOperatorCounts(t));
					break;
				case Op_subsub:
					countsOfOperators.put(TokenType.Op_subsub, findOperatorCounts(t));
					break;
				case Op_multiply:
					countsOfOperators.put(TokenType.Op_multiply, findOperatorCounts(t, false));
					break;
				default://digerleri icin asagidaki gibi bul
					countsOfOperators.put(t, findOperatorCounts(t));
					break;
					
			}
			
		}
		
		//operatorleri tek tek ekrana yazdirmak icin //
		/*
		for(TokenType t:operatorTypes) {
			
			System.out.println(t.name()+"="+countsOfOperators.get(t).toString());
		}
		System.out.println(TokenType.Op_singleSub.name()+"="+countsOfOperators.get(TokenType.Op_singleSub).toString());
		System.out.println(TokenType.Op_singleAdd.name()+"="+countsOfOperators.get(TokenType.Op_singleAdd).toString());*/
		return countsOfOperators;
	}
	public void printOperators() {//operatorleri tektek yazdiran method
		
		for(TokenType t:operatorTypes) {
			
			System.out.println(t.name()+"="+countsOfOperators.get(t).toString());
		}
		System.out.println(TokenType.Op_singleSub.name()+"="+countsOfOperators.get(TokenType.Op_singleSub).toString());
		System.out.println(TokenType.Op_singleAdd.name()+"="+countsOfOperators.get(TokenType.Op_singleAdd).toString());
	}
	
	public int findOperatorCounts(TokenType typ) {//direkt tokenlarin icindeki operatorleri sayan method
		int counter=0;
		for(Token t:tokens) {
			if(typ==t.tokentype) {
				counter++;
			}
		}
		return counter;
	}
	public int findOperatorCounts(TokenType typ,boolean single) {//+ - gibi hem tekli hem ikili olan operatorleri ayirt etmek icin
		arr_size=tokens.size();
		Token oldToken;
		Token secondToken;
		int op_counter=0;
		
		
		for(int i=0;position<arr_size;i++) {
			oldToken=getNextToken();
			secondToken=getNextToken();
			getNextToken();
			
			boolean rightSide=(tkn.tokentype==TokenType.Identifier ||	//operatorun sag tarafina gelebilecek olan ifadeler
					tkn.tokentype==TokenType.LeftParen||
					tkn.tokentype==TokenType.Integer ||
					tkn.tokentype==TokenType.Op_plusplus||
					tkn.tokentype==TokenType.Op_subsub||
					tkn.tokentype==TokenType.Op_add||
					tkn.tokentype==TokenType.Op_subtract||
					tkn.tokentype==TokenType.Op_not);
			boolean leftSide;
			if(single) {
				 
				leftSide=(oldToken.tokentype!=TokenType.RightParen &&	//tekli operator ise soluna ne gelmeyecek olduguna bakan method
						oldToken.tokentype!=TokenType.Identifier&&
						oldToken.tokentype!=TokenType.Integer&&
						oldToken.tokentype!=TokenType.String&&
						oldToken.tokentype!=TokenType.Op_plusplus&&
						oldToken.tokentype!=TokenType.Op_subsub);
				
				
			}
			
			else {
				leftSide=(oldToken.tokentype==TokenType.RightParen ||//ikili operator ise soluna ne gelecegine bakan method
						oldToken.tokentype==TokenType.Identifier||
						oldToken.tokentype==TokenType.Integer||
						oldToken.tokentype==TokenType.Op_plusplus||
						oldToken.tokentype==TokenType.Op_subsub);
			}
		
			
			if( leftSide && secondToken.tokentype==typ && rightSide) //saginin ve solunu kontrol eden if
			{
				op_counter++;
				
			}
			
			
		}
		this.position=0;//tekrar calistirildiginda sifirdan baslamasi icin
		this.pos=0;
		
		return op_counter;
	}
	 private Token getNextToken() {//siradaki 3 tokeni getirir sonra 2 geri gider tekrar 3 ileri gider 3 token getirir
	             
	        if (this.position < this.arr_size) {
	           
	        	this.tkn=tokens.get(position);
	        	if(this.pos%3==0 &&pos!=0) {
		        	this.position-=2;
		       
		        	this.tkn=tokens.get(position);
		        }
	        }
	        this.position++;//pozisyon bilgisi
	        this.pos++;//tekrar bilgisi
	        
	       
	        
	      
	        return this.tkn;
	    }
	
}

