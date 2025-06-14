package ast;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import ast.instruccion.CreacionTupla;
import ast.instruccion.Declaracion;
import ast.instruccion.Instruccion;
import ast.instruccion.funciones.Funcion;
import ast.instruccion.funciones.Parametro;
import ast.tipos.*;

public class Programa extends ASTNode{
	
	private ArrayList<Instruccion> instGlob;
	private static TablaSimbolos pila;
	private static int fin = 0;
	private static int indMain = 0;
	private ArrayList<ASTNode> funsIO;
	
	public static PrintWriter codigo;
	

	public Programa(ArrayList<Instruccion> iG){
		instGlob = iG;
		pila = new TablaSimbolos();
		funsIO = new ArrayList<ASTNode>();
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < instGlob.size(); i++){
			s += "\t" + instGlob.get(i).toString() + "\n";
		}
		return "PROGRAMA(\n" + s + "\n)";
	}

	@Override
	public NodeKind nodeKind(){
		return NodeKind.PROGRAMA;
	}

	@Override
	public void checkTipo() {
		for(ASTNode n: funsIO)
			n.checkTipo();
		
		for(Instruccion inst : instGlob)
			inst.checkTipo();
	}

	private void iniFunciones() {
		ArrayList<Parametro> params1 = new ArrayList<Parametro>();
		params1.add(new Parametro(new TBasicos(KindT.TINT), "x"));
		Funcion f1 = new Funcion("write", new TBasicos(KindT.EMPT), params1, new ArrayList<Instruccion>());
		funsIO.add(f1);
		pila.insertar("write", f1); 
		
		ArrayList<Parametro> params2 = new ArrayList<Parametro>();
		params2.add(new Parametro(new TBasicos(KindT.TBOO), "x"));
		Funcion f2 = new Funcion("write", new TBasicos(KindT.EMPT), params2, new ArrayList<Instruccion>());
		funsIO.add(f2);
		pila.insertar("write", f2);

		ArrayList<Parametro> params3 = new ArrayList<Parametro>();
		params3.add(new Parametro(new TBasicos(KindT.TINT), "x"));
		Funcion f3 = new Funcion("read", new TBasicos(KindT.EMPT), params3, new ArrayList<Instruccion>());
		pila.insertar("read", f3);
		funsIO.add(f3);

		ArrayList<Parametro> params4 = new ArrayList<Parametro>();
		params4.add(new Parametro(new TBasicos(KindT.TBOO), "x"));
		Funcion f4 = new Funcion("read", new TBasicos(KindT.EMPT), params4, new ArrayList<Instruccion>());
		pila.insertar("read", f4);
		funsIO.add(f4);
	}
	
	@Override
	public void bind() {
		pila.abrirBloque();
		iniFunciones();
		
		for (Instruccion inst: instGlob)
			inst.bind();
		
		if (indMain == 0){
			System.out.println("ERROR: no hay ninguna función main declarada en PROGRAMA"); 
		}
		
		pila.print();
		pila.cerrarBloque();
		
	}
	
	public static void insertar(String iden, ASTNode nodo){
		pila.insertar(iden, nodo);
	}

	public static void abrirBloque(){
		pila.abrirBloque();
	}

	public static void cerrarBloque(){
		pila.cerrarBloque();
	}

	public static ArrayList<ASTNode> searchListNode(String iden){
		return pila.searchListNode(iden);
	}
	
	public static ASTNode searchNode(String iden){
		return pila.searchNode(iden);
	}
	
	public static ASTNode searchNode(String iden, ArrayList<Parametro> params){
		return pila.searchNode(iden, params);
	}
	

	public int getFin(){
		return fin;
	}	

	public static void setFin(){
		fin = 1;
	}

	public static void addMain(){
		indMain = 1;
	}

	public static void print() {
		pila.print();
	}
	
	public static void printAll(){
		pila.printAll();
	}

	public static int getSize() {
		return pila.getSize();
	}

	

	
	

	public void generaCodigo(){
		try{

		codigo = new PrintWriter(new FileWriter("codigo.wat"));
		FileReader inicio = new FileReader("generacion/inicio.wat");
		inicio.transferTo(codigo);
		//inicio.close();
		codigo.println("\ti32.const " + maxMemoria());
		codigo.println("\tcall $reserveStack");
		codigo.println("\tlocal.set $temp");
		codigo.println("\tglobal.get $MP");
		codigo.println("\tlocal.get $temp");
		codigo.println("\ti32.store");
		codigo.println("\tglobal.get $MP");
		codigo.println("\ti32.const 4");
		codigo.println("\ti32.add");
		codigo.println("\tlocal.set $localsStart\n");
		
		Instruccion instMain = null;
		for(Instruccion inst : instGlob){
			if (inst instanceof Declaracion){
				inst.generaCodigo();
			}
			else if (inst instanceof Funcion){
				Funcion fun = (Funcion) inst;
				if (fun.getIden().equals("main")){
					instMain = inst;
				}
			}
			Programa.codigo.println("");
		}
		
		codigo.println("\tcall $main");
		codigo.println("\ti32.load");
		codigo.println("\tcall $printi32");
		codigo.println("\tcall $freeStack");
		codigo.println(")");
		
		instMain.generaCodigo();
		Programa.codigo.println("");
		
		for(Instruccion inst : instGlob){
			if (!(inst instanceof Declaracion) && !(inst instanceof CreacionTupla) && !(inst.equals(instMain))){
				inst.generaCodigo();
				Programa.codigo.println("");
			}
		}
	
		inicio = new FileReader("generacion/final.wat");
		codigo.println("\n\n\n ;;FUNCIONES DEFINIDAS");
		inicio.transferTo(codigo);
		inicio.close();
          	codigo.close();
		} catch(Exception e){
			System.err.println(e.getStackTrace());
			e.printStackTrace();
			Programa.setFin();
			
		}
	}


	public int maxMemoria(){
		int max = 0;
		for(Instruccion ins : instGlob){
			if(ins instanceof Declaracion) { 
				max += ins.getTamanyo();
			}
		}
		return max + 4;
	}
}
