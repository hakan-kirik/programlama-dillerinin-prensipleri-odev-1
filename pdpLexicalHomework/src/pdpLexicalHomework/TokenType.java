/**
*
* @author HAKAN KIRIK 
* 		hakan.kirik1@ogr.sakarya.edu.tr || hakankirik7@gmail.com
* B201210370
* 
* @since 23.03.22
* <p>
* Bu enumarator token tiplerini tanimlar
* 
* </p>
*/
package pdpLexicalHomework;

public enum TokenType {//operatorlerin ve diger keywordlerin token tiplerinin tutuldugu enumarator
    //operatorler
    Op_multiply, 
    Op_divide,
    Op_mod, 
    Op_add,
    Op_subtract,
    Op_not, 
    Op_less,
    Op_lessequal,
    Op_greater,
    Op_greaterequal,
    Op_equal, 
    Op_notequal,
    Op_assign,
    Op_and, 
    Op_or,
    Op_plusplus,
    Op_plusAssign,
    Op_subAssign,
    Op_subsub,
    Op_mulAssign,
    Op_bitwiseAnd,
    Op_bitwiseOr,
    Op_modAssign,
    Op_andAssign,
    Op_orAssign,
    Op_divAssign,
    Op_XORAssign,
    Op_bitwiseXOR,
    Op_singleAdd,
    Op_singleSub,
    //parantezler ve keywordler
    LeftParen, 
    RightParen,
    LeftBrace,
    RightBrace, 
    RightBracket,
    LeftBracket,
    Semicolon, 
    Comma, 
    Identifier,
    Integer, 
    String,
    Char,
    Uchar,
    Dot,
    Keyword,
    generic,
    //dosya sonu
    End_of_input
}

