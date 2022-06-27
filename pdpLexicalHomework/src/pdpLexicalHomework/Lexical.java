/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 26.03.22
* <p>
* Bu sinif operator ve operand bilgilerini tutar ve OperatorCounter sinifindan aldigi operator sayisilarini operator turlerine gore ekrana yazdirir
* 
* </p>
*/
package pdpLexicalHomework;

import java.util.ArrayList;
import java.util.Map;

public class Lexical {
	private Map<TokenType, Integer> operatorsAndCount;//operatorlerin tipleri ve sayilarini tutuldugu hasmap
	private TokenType[] SingleOperatorCount;//tekli operator tiplerinin tutuldugu dizi
	private TokenType[] binaryOperators;//ikili operator tiplerinin tutuldugu dizi
	private TokenType[]	relationalOperators;//iliskisal operator tiplerinin tutuldugu dizi
	private TokenType[] logicalOperators;//mantiksal operatorlerin tiplerinin tutuldugu dizi
	private TokenType[]	arithmeticOperators;//sayisal operatorlerinin tiplerinin tutldugu dizi
	private int op_not;//not operatorunun sayisi //operand sayisini bulmak icin
	private OperatorCounter operatorsCounts;
	public Lexical(String fileName) {
		//operator tiplerinin diziye tanimlanmasi
		SingleOperatorCount=new TokenType[] {TokenType.Op_singleAdd,TokenType.Op_singleSub,TokenType.Op_plusplus,TokenType.Op_subsub,TokenType.Op_not};
		
		binaryOperators= new TokenType[]{TokenType.Op_multiply,TokenType.Op_divide,TokenType.Op_mod, 
				TokenType.Op_add,TokenType.Op_subtract,
				TokenType.Op_assign,
				TokenType.Op_plusAssign,TokenType.Op_subAssign,
				TokenType.Op_mulAssign,TokenType.Op_bitwiseAnd,TokenType.Op_bitwiseOr,
				TokenType.Op_modAssign,TokenType.Op_andAssign,TokenType.Op_orAssign,
			    TokenType.Op_divAssign,TokenType.Op_XORAssign, TokenType.Op_bitwiseXOR};
		
		relationalOperators=new TokenType[] {TokenType.Op_equal,TokenType.Op_notequal,TokenType.Op_less,TokenType.Op_lessequal,TokenType.Op_greater,TokenType.Op_greaterequal};
		logicalOperators=new TokenType[] {TokenType.Op_and,TokenType.Op_or,TokenType.Op_not};
		arithmeticOperators=new TokenType[] {TokenType.Op_multiply,TokenType.Op_divide,TokenType.Op_mod, 
				TokenType.Op_add,TokenType.Op_subtract,
				TokenType.Op_assign,
				TokenType.Op_plusAssign,TokenType.Op_subAssign,
				TokenType.Op_mulAssign,TokenType.Op_bitwiseAnd,TokenType.Op_bitwiseOr,
				TokenType.Op_modAssign,TokenType.Op_andAssign,TokenType.Op_orAssign,
			    TokenType.Op_divAssign,TokenType.Op_XORAssign, TokenType.Op_bitwiseXOR,TokenType.Op_subsub,TokenType.Op_plusplus,TokenType.Op_singleAdd,TokenType.Op_singleSub};
		//operator sayacindan nesne olusturulmasi
		 operatorsCounts=new OperatorCounter(fileName);
		//operator tiplerinin ve sayilarinin hasmap e atanmasi
		operatorsAndCount=operatorsCounts.countOperators();
		op_not=operatorsCounts.findOperatorCounts(TokenType.Op_not);//hem mantiksal hem tekli operator olan not operatorunun sayisini bulup operand sayisinda hatayi onluyoruz
		
	}
	private int getTotalOperatorsCount(TokenType[] operatorTypes)//operator tipine gore operatorlerin sayisin toplamini dondurur
	{
		int total=0;
		for(TokenType t:operatorTypes) {//opetor tiplerini dolasir ve toplam sayisini dondurur
			total+=operatorsAndCount.get(t);
		}
		return total;
	}
	public void calculateOfWholeOperatorCount() {//tum operatorlerin operator sayisini hesaplar
		
		ArrayList<TokenType[]> fields=new ArrayList<TokenType[]>();//tek tek operator tanimlamalarina dongu acmamak icin hepsi dizide tanimlamis ve tek dongu ile yazilmistir
		fields.add(SingleOperatorCount);
		fields.add(binaryOperators);
		fields.add(arithmeticOperators);
		fields.add(relationalOperators);
		fields.add(logicalOperators);
		System.out.println("Operator Bilgisi:");
		int i=0,operand=0;
		for(TokenType[] arr:fields) {
			int total=0;
			total=getTotalOperatorsCount(arr);
			i++;
			switch(i) {
			case 1:
				System.out.println("\tTekli Operator Sayisi: "+total);
				operand+=total;
				break;
			case 2:
				System.out.println("\tIkili Operator Sayisi: "+total);
				operand+=total*2;
				break;
			case 3:
				System.out.println("\tSayisal Operator Sayisi: "+total);
				break;
			case 4:
				System.out.println("\tIliskisel Operator Sayisi: "+total);
				operand+=total*2;
				break;
			case 5:
				System.out.println("\tMantiksal Operator Sayisi: "+total);
			
				operand+=total*2-op_not*2;
			}
			
			
		}
		System.out.println("Operand Bilgisi:");
		System.out.println("\tToplam Operand Sayisi: "+operand);
		
	}
	public void printOperators() {//operatorleri ve sayilarini tek tek yazdiran method
		operatorsCounts.printOperators();
	}

}
