package ast;

import java.util.ArrayList;
import java.util.HashMap;

import ast.instruccion.Declaracion;
import ast.instruccion.funciones.Funcion;
import ast.instruccion.funciones.Parametro;
import ast.tipos.TArray;

public class TablaSimbolos {
	  
    private ArrayList<HashMap<String, ArrayList<ASTNode>>> pila;
    private ArrayList<ASTNode> deltaList;
    private int cont = 0;
    
    
    public TablaSimbolos() {
    	pila = new ArrayList<HashMap<String, ArrayList<ASTNode>>>();
    	deltaList = new ArrayList<ASTNode>();
    }
    
    public void insertar (String id, ASTNode nodo){
    	nodo.setCont(cont);
    	cont++;
        if(pila.size() != 0){
        	System.out.println("INSERTAMOS " + nodo);
            HashMap<String,ArrayList<ASTNode>> top = pila.get(pila.size() - 1);
            if(!top.containsKey(id)) {
            	ArrayList<ASTNode> aux = new ArrayList<ASTNode>();
            	aux.add(nodo);
            	top.put(id, aux);            	
            }
            else top.get(id).add(nodo);
            
            if(nodo instanceof Declaracion || nodo instanceof Parametro) {
            	nodo.setPrevNode(deltaList.getLast());
	        	deltaList.removeLast();
	        	deltaList.add(nodo);
	        	
	        	// EN realidad, no bastaría linkar cada nodo con el que venga antes en el binding, de 
	        	// forma que el delta de un nodo es el delta + tamaño de su anterior? 
	        	// Esta forma de plantearlo permite que la estructura arborescente del binding quede
	        	// representada sin ninguna estructura de datos.
	        	
            }
            
        }
    }
    
    
    public void abrirBloque(){
        pila.add(new HashMap<String,ArrayList<ASTNode>>());
        if(pila.size() <= 2)
            deltaList.add(null);
        else deltaList.add(deltaList.getLast());
    }

    public void cerrarBloque(){
        if (pila.size() != 0){ 
            pila.remove(pila.size()-1); 
        }
        deltaList.removeLast();
    }
    
    public ArrayList<ASTNode> searchListNode(String id){
    	ArrayList<ASTNode> lNodo = null;
        
        for (int i = pila.size() - 1; i >= 0 && lNodo == null; i--){
        	HashMap<String, ArrayList<ASTNode>> last = pila.get(i);
        	lNodo = last.get(id);
        } 
        
        return lNodo;
	}
    
    public ASTNode searchNode(String id){//primera aparicion de id en la pila
        ASTNode nodo = null;
        
        for (int i = pila.size() - 1; i >= 0 && nodo == null; i--){
        	HashMap<String, ArrayList<ASTNode>> last = pila.get(i);
        	ArrayList<ASTNode> listaNodos = last.get(id);

        	if(listaNodos != null) {
	        	for(ASTNode n : listaNodos) {
	        		if(!(n instanceof Funcion)) {
	        			nodo = n;
	        			break;
	        		}
	        	}
        	}
        } 
        
        return nodo;
    }
    
    
    public ASTNode searchNode(String id, ArrayList<Parametro> params){//primera aparicion de id en la pila
    	ASTNode nodo = null;
        
        for (int i = pila.size() - 1; i >= 0 && nodo == null; i--){
        	HashMap<String, ArrayList<ASTNode>> last = pila.get(i);
        	ArrayList<ASTNode> listaNodos = last.get(id);
        	
        	if(listaNodos != null) {
	        	for(ASTNode n : listaNodos) {
	        		if((n instanceof Funcion) && compareFunParams(params, ((Funcion) n).getParams())) {
	        			nodo = n;
	    				break;
	        		}
	        	}
        	}
        } 
        
        return nodo;
    }
    
    private boolean compareFunParams(ArrayList<Parametro> params, ArrayList<Parametro> p) {
    	if(p.size() != params.size()) 
    		return false;
    	
		for(int i = 0; i < params.size(); i++) {
			if( !(params.get(i).getTipo().equals(p.get(i).getTipo()))) {
				return false;
			}
		}
		
    	return true;
    }
    
    public int getSize(){
    	return pila.size();
    }
    
    
    private void printAmbito(HashMap<String,ArrayList<ASTNode>> ambito){
        for (HashMap.Entry<String, ArrayList<ASTNode>> entry : ambito.entrySet()) {
            String key = entry.getKey();
            ArrayList<ASTNode> value = entry.getValue();
            System.out.println(key + " -> " + value);
        }
    }

    public void print(){
		//for(int i = 0; i < pilaTabla.size(); i++){
		if (pila.size() != 0){
			System.out.println("INICIO AMBITO");
			HashMap<String,ArrayList<ASTNode>> last = pila.get(pila.size() - 1);
			printAmbito(last);
			System.out.println("FIN AMBITO");
	
		}
	        else {
			System.out.println("Pila Vacia");
		}
    }
    
    public void printAll(){
    	for(int i = 0; i < pila.size(); i++){
    		System.out.println("INICIO AMBITO");
    		HashMap<String,ArrayList<ASTNode>> last = pila.get(i);
    		printAmbito(last);
    		System.out.println("FIN AMBITO");

    	}
    }


}
