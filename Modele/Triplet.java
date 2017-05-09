package Modele ;

public class Triplet <C1, C2, C3> {

	protected C1 c1 ;
	protected C2 c2 ;
	protected C3 c3 ;
	
	public Triplet (C1 c1, C2 c2, C3 c3) {
		
		this.c1 = c1 ;
		this.c2 = c2 ;
		this.c3 = c3 ;
		
	}
	
	public Triplet (Triplet <C1, C2, C3> c) {
		
		this.c1 = c.c1 ;
		this.c2 = c.c2 ;
		this.c3 = c.c3 ;
		
	}

	public C1 getC1() {
		return c1;
	}

	public void setC1(C1 c1) {
		this.c1 = c1;
	}

	public C2 getC2() {
		return c2;
	}

	public void setC2(C2 c2) {
		this.c2 = c2;
	}
	
	public C3 getC3() {
		return c3;
	}

	public void setC3(C3 c3) {
		this.c3 = c3;
	}

	@Override
	public String toString() {
		return "Triplet [c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + "]";
	}	
}

