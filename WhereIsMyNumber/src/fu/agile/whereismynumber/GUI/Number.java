package fu.agile.whereismynumber.GUI;

public class Number{
	private int number;
	private boolean isTicked;
	
	public Number(int number) {
		super();
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isTicked() {
		return isTicked;
	}
	public void setTicked(boolean isTicked) {
		this.isTicked = isTicked;
	}
	
	@Override
	public String toString(){
		return ""+number;
	}
	
	public boolean equals(Number x){
		return (this.number == x.getNumber());
		
	}
}
