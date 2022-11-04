package ships;
//array de caracteres tam de ese array según el tamano
public class Ship {
	private String name;
	private int size;
	private char letter;
	private boolean vertical;
	private int id;
	
	public Ship(String n,int s,char l, boolean v, int id) {
		this.name=n;
		this.size=s;
		this.letter=l;
		this.vertical= v;
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
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
