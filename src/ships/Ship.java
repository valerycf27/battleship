package ships;

public class Ship {
	private String name;
	private int size;
	private char letter;
	private boolean vertical;
	private int id;
	
	private char[] arrayShip;
	private int counterArray;
	
	public Ship(String n,int s,char l, boolean v, int id) {
		this.name=n;
		this.size=s;
		this.letter=l;
		this.vertical= v;
		this.id=id;
		
		this.arrayShip= new char[this.size];
		for (int i=0;i<this.size;i++) {
			arrayShip[i]=this.letter;
		}
		this.counterArray=0;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public char getLetter() {
		return letter;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public int getId() {
		return id;
	}	
	
	public char[] getArrayShip() {
		return arrayShip;
	}

	public void setArrayShip(char[] arrayShip) {
		this.arrayShip = arrayShip;
	}
	
	public int getCounterArray() {
		return counterArray;
	}

	public void setCounterArray(int counterArray) {
		this.counterArray = counterArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + letter;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		result = prime * result + (vertical ? 1231 : 1237);
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
		Ship other = (Ship) obj;
		if (letter != other.letter)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		if (vertical != other.vertical)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
