grammar CAP;

@header {
package org.tzi.use.examplePlugin.parser;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.tzi.use.examplePlugin.ast.*;
}

@lexer::header {
package org.tzi.use.examplePlugin.parser;
}

@members {
    String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }
}

/* =======================
   ENTRY
======================= */

annotationFile returns [ASTInterface ann]
@init { $ann = null; }
    :
      a=annotation EOF
      { $ann = $a.n; }
    ;

/* =======================
   ANNOTATION
======================= */

annotation returns [ASTInterface n]
@init {
    $n = new ASTInterface(); // <-- MUST be concrete class
}
    :
      (AT name=IDENT | name=IDENT)
      {
        $n.name = $name.getText();
      }
      LPAREN
        arguments[$n]?
      RPAREN
    ;

/* =======================
   ARGUMENTS
======================= */

arguments[ASTInterface ann]
    :
      argument[ann] (COMMA argument[ann])*
    ;

argument[ASTInterface ann]
    :
      key=IDENT EQ v=value
      {
        ann.args.put($key.getText(), $v.val);
      }
    ;

/* =======================
   VALUES
======================= */

value returns [Object val]
    :
      s=STRING
        { $val = stripQuotes($s.getText()); }
    | n=NUMBER {
        String txt = $n.getText();
        if (txt.contains(".")) {
            $val = Double.parseDouble(txt);
        } else {
            $val = Integer.parseInt(txt);
        }
    }
    | q=QUALIFIED_IDENT
        { $val = $q.getText(); }
    | i=IDENT
        { $val = $i.getText(); }
    | a=annotation
        { $val = $a.n; }
    | arr=array
        { $val = $arr.list; }
    ;

/* =======================
   ARRAY
======================= */

array returns [List<Object> list]
@init { $list = new ArrayList<Object>(); }
    :
      LBRACE
        v=value { $list.add($v.val); }
        (COMMA v=value { $list.add($v.val); })*
      RBRACE
    ;

/* =======================
   LEXER
======================= */

AT      : '@' ;
LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACE  : '{' ;
RBRACE  : '}' ;
COMMA   : ',' ;
EQ      : '=' ;

NUMBER
    : '-'? ('0'..'9')+ ('.' ('0'..'9')+ )?
    ;

QUALIFIED_IDENT
    : ('a'..'z' | 'A'..'Z' | '_')
      ('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | '.')*
      ('::'
        ('a'..'z' | 'A'..'Z' | '_')
        ('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | '.')*
      )+
    ;

IDENT
    : ('a'..'z' | 'A'..'Z' | '_')
      ('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | '.')*
    ;

STRING
    : '\'' ( ~('\'' | '\\') | '\\' . )* '\''
    ;

WS
    : (' ' | '\t' | '\r' | '\n')+ {skip();}
    ;
