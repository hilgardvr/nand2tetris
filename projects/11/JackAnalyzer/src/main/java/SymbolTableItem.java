public class SymbolTableItem {
	private String name;
	private String category;
	private String type;
	private int index;
	static int fieldIndex;
	static int staticIndex;
	static int argumentIndex;
	static int localIndex;

	SymbolTableItem(String n, String c, String t) {
		this.name = n;
		this.category = c;
		this.type = t;
		switch (c) {
			case "field":
				this.index = SymbolTableItem.fieldIndex;
				SymbolTableItem.fieldIndex++;
				break;
			case "static":
				this.index = SymbolTableItem.staticIndex;
				SymbolTableItem.staticIndex++;
				break;
			case "argument":
				this.index = SymbolTableItem.argumentIndex;
				SymbolTableItem.argumentIndex++;
				break;
			case "local":
				this.index = SymbolTableItem.localIndex;
				SymbolTableItem.localIndex++;
				break;
			default:
				System.out.println("Could not find " + c);
				break;
		}
		System.out.println("adding new item " + toString());
		return;
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
	
	public String toString() {
		return (this.category + ": " + this.name + " - index: " + Integer.toString(this.index));
	}
}
