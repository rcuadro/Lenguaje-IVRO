package ast.instruccion.bucle;

import ast.instruccion.Instruccion;

public abstract class Bucle extends Instruccion{
	public abstract String toString();

	@Override
	public boolean hasBlock() {
		return true;
	}
}
