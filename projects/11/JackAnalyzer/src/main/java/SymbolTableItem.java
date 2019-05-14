import java.util.List;
import java.util.ArrayList;

public class SymbolTableItem {
	private String scope;
	private String name;
	private String type;
	private String kind;
	private int index;
	static int fieldIndex;
	static int staticIndex;
	static int argumentIndex;
	static int localIndex;

	SymbolTableItem(String s, String n, String t, String k) {
		this.scope = s;
		this.name = n;
		this.type = t;
		this.kind = k;
		switch (kind) {
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
				System.out.println("Could not find " + k);
				break;
		}
		return;
	}
}
