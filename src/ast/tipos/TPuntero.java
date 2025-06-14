package ast.tipos;

public class TPuntero extends Tipo{
	private Tipo tipo;

	public TPuntero(Tipo tipo){
		this.tipo = tipo;
	}	
	
	@Override
	public String toString(){
		return "PUNTERO(" + tipo.toString() + ")";
	}
	
	@Override
	public Tipo getTipo() {
		return tipo;
	}
	
	@Override
	public void checkTipo() {
		tipo.checkTipo();
		setTipo(this);
	}

	@Override
	public void bind() {
		tipo.bind();
	}

	@Override
	public int getTam() {
		return 4;
	}
}
