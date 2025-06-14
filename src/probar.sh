#!/usr/bin/bash

# Esta sección de código compila el proyecto:
echo "COMPILACIÓN:"
cd alex
java -cp jflex.jar jflex.Main AnalizadorLexicoExp.l
cd ..

cd constructorAst
java -cp cup.jar java_cup.Main -parser AnalizadorSintacticoExp -symbols ClaseLexica -nopositions ConstructorAST.cup
cd ..

javac -cp ".:constructorAst/cup.jar" */*.java
javac -cp ".:constructorAst/cup.jar" alex/*.java

# Se compila el ejemplo seleccionado
echo "Compilamos el ejemplo número $1"
if [ "$1" -eq 1 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_bucles.txt
elif [ "$1" -eq 2 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_ctes_condicionales.txt
elif [ "$1" -eq 3 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_denote_incase_matriz.txt
elif [ "$1" -eq 4 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_funciones_sobrecarga.txt
elif [ "$1" -eq 5 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_IO_punteros.txt
elif [ "$1" -eq 6 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_recursion.txt
elif [ "$1" -eq 7 ]; then
	java -cp ".:constructorAst/cup.jar" constructorAst.Main tests/ejemplo_tuplas.txt
fi

cd generacion
wat2wasm ../codigo.wat
if [ "$1" -eq 5 ]; then
	node main.js 2
elif [ "$1" -eq 6 ]; then
	node main.js 1
else
	node main.js 0
fi




