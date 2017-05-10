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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
		result = prime * result + ((c3 == null) ? 0 : c3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		if (c3 == null) {
			if (other.c3 != null)
				return false;
		} else if (!c3.equals(other.c3))
			return false;
		return true;
	}	
}

