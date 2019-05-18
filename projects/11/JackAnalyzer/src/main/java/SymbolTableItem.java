public class SymbolTableItem {
	private String name;
	private String category;
	private String type;
	private int index;
	/*static int fieldIndex;
	static int staticIndex;
	static int argumentIndex;
	static int localIndex;*/

	SymbolTableItem(String n, String c, String t, int index) {
		this.name = n;
		this.category = c;
		this.type = t;
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public String getCategory() {
		return this.category;
	}

	public String getType() {
		return this.type;
	}

	public int getIndex() {
		return this.index;
	}
	
	public String toString() {
		return (this.category + "\t" + this.type + "\t" + this.name + "\t" + Integer.toString(this.index));
	}
}
