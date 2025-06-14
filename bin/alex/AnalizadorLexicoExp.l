package alex;

import errors.GestionErroresExp;

%%
%cup
%line
%column
%class AnalizadorLexicoExp
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresExp errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresExp errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}



separador = [ \t\r\b\n]

//PALABRAS_RESERVADAS:TIPOS
PR_tipoInt = int
PR_tipoBool = bool
PR_elemBool = True|False
PR_tuple = tuple

//PALABRAS_RESERVADAS:ALIAS_Y_CONSTANTES
PR_denote = denote
PR_as = as
PR_cte = cte

//PALABRAS_RESERVADAS:CONDICIONALES
PR_if = if
PR_else = else

//PALABRAS_RESERVADAS:BUCLES
PR_for = for
PR_while = while
PR_repeat = repeat
PR_until = until

//PALABRAS_RESERVADAS:SWITCH-CASE
PR_incase = incase
PR_equals = equals
PR_otherwise = otherwise

//PALABRAS_RESERVADAS:ENTRADA/SALIDA
PR_read = read
PR_write = write

//PALABRAS_RESERVADAS:OTRAS
PR_empty = empty
PR_return = return
PR_newMem = newMem
PR_delete = delete
PR_main = main


//SIMBOLOS:COMENTARIOS
commentSingle = \/\/[^\n]* 
commentMulti = \# ~ \#

//SIMBOLOS:COMPARACION_Y_ASIGNACION
eq = \=
not = \!
neq = {not}{eq}
lesser = \<
greater = \>
geq = {greater}{eq}
leq = {lesser}{eq}
asignacion = {dosPuntos}{eq}

//SIMBOLOS:CON_APERTURA_Y_CIERRE
llaveApertura = \{
llaveCierre = \}
corchApertura = \[
corchCierre = \]
parentApertura = \(
parentCierre = \)

//SIMBOLOS:PUNTUACION
dosPuntos = \:
puntoYComa = \;
coma = \,
punto = \.

//SIMBOLOS:PUNTEROS
interrogacion = \?
arroba = \@

//SIMBOLOS:ARITMETICOLOGICOS
suma = \+
resta = \-
producto = \*
division = \/
potencia = \^
modulo = \%

and = \&
or = \|

//SIMBOLOS:FLECHAS
entonces = {eq}{greater}
flecha = {resta}{greater}


//CARACTERES:
letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)

//Repasar_esto:
identificador = ({letra})({letra}|{digito}|\_)*
parteInt = ({digitoPositivo}{digito}*) | 0
elemInt = [\+\-]?{parteInt}

%%
{separador}			{}
{commentSingle}			{}
{commentMulti}			{}

//PALABRAS_RESERVADAS:TIPOS
{PR_tipoInt}			{return ops.unidadTipoInt();}	
{PR_tipoBool}			{return ops.unidadTipoBool();}
{PR_elemBool}			{return ops.unidadElemBool();}
{PR_tuple}			{return ops.unidadTuple();}

//PALABRAS_RESERVADAS:ALIAS_Y_CONSTANTES
{PR_denote}		  	{return ops.unidadDenote();}
{PR_as}		  		{return ops.unidadAs();}
{PR_cte}			{return ops.unidadCte();}

//PALABRAS_RESERVADAS:CONDICIONALES
{PR_if}				{return ops.unidadIf();}
{PR_else}			{return ops.unidadElse();}

//PALABRAS_RESERVADAS:BUCLES
{PR_for}			{return ops.unidadFor();}
{PR_while}			{return ops.unidadWhile();}
{PR_repeat}			{return ops.unidadRepeat();}
{PR_until}			{return ops.unidadUntil();}

//PALABRAS_RESERVADAS:SWITCH-CASE
{PR_incase}			{return ops.unidadIncase();}
{PR_equals}			{return ops.unidadEquals();}
{PR_otherwise}			{return ops.unidadOtherwise();}

//PALABRAS_RESERVADAS:ENTRADA/SALIDA
{PR_read}			{return ops.unidadRead();}
{PR_write}			{return ops.unidadWrite();}

//PALABRAS_RESERVADAS:OTRAS
{PR_empty}			{return ops.unidadEmpty();}
{PR_return}			{return ops.unidadReturn();}
{PR_newMem}			{return ops.unidadNewMem();}
{PR_delete}			{return ops.unidadDelete();}
{PR_main}			{return ops.unidadMain();}


//SIMBOLOS:COMPARACION_Y_ASIGNACION
{eq}				{return ops.unidadEq();}
{not}				{return ops.unidadNot();}
{neq}				{return ops.unidadNeq();}
{lesser}			{return ops.unidadLesser();}
{greater}			{return ops.unidadGreater();}
{geq}				{return ops.unidadGeq();}
{leq}				{return ops.unidadLeq();}
{asignacion}			{return ops.unidadAsig();}

//SIMBOLOS:CON_APERTURA_Y_CIERRE
{llaveApertura}		  	{return ops.unidadLlaveApert();}
{llaveCierre}		  	{return ops.unidadLlaveCierre();}
{corchApertura}		  	{return ops.unidadCorchApert();}
{corchCierre}		  	{return ops.unidadCorchCierre();}
{parentApertura}		{return ops.unidadParentApert();}
{parentCierre}			{return ops.unidadParentCierre();}

//SIMBOLOS:PUNTUACION
{dosPuntos}			{return ops.unidadDosPuntos();}
{puntoYComa}			{return ops.unidadPyC();}
{coma}		  		{return ops.unidadComa();}
{punto}		  		{return ops.unidadPunto();}

//SIMBOLOS:PUNTEROS
{interrogacion}		  	{return ops.unidadInterrogacion();}
{arroba}		  	{return ops.unidadArroba();}

//SIMBOLOS:ARITMETICOLOGICOS
{suma}				{return ops.unidadSuma();}
{resta}				{return ops.unidadResta();}
{producto}			{return ops.unidadProducto();}
{division}			{return ops.unidadDivision();}
{potencia}			{return ops.unidadPotencia();}
{modulo}			{return ops.unidadModulo();}

{and}				{return ops.unidadAnd();}
{or}				{return ops.unidadOr();}

//SIMBOLOS:FLECHAS
{entonces}			{return ops.unidadEntonces();}
{flecha}			{return ops.unidadFlecha();}

{elemInt}			{return ops.unidadElemInt();}
{identificador}			{return ops.unidadId();}


[^]                       	{errores.errorLexico(fila(),columna(),lexema());}  
