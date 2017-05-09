package Modele;


public class Couple <C1, C2> {
	
	private C1 c1 ;
	private C2 c2 ;
	
	public Couple (C1 c1, C2 c2) {
		
		this.c1 = c1 ;
		this.c2 = c2 ;
		
	}
	
	public Couple (Couple <C1, C2> c) {
		
		this.c1 = c.c1 ;
		this.c2 = c.c2 ;
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
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
		@SuppressWarnings("rawtypes")
		Couple other = (Couple) obj;
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
		return true;
	}
	
	
	
}

