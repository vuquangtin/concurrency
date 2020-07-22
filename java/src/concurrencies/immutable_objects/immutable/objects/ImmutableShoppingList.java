package immutable.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collections
 * 
 * Good news! java.util.Collections provides a number of convenience methods
 * that make converting a Collection to an UnmodifiableCollection a snap. <br/>
 * Check out:
 * <ul>
 * <li>
 * Collections.unmodifiableCollection</li>
 * <li>
 * Collections.unmodifiableList</li>
 * <li>
 * Collections.unmodifiableMap</li>
 * <li>
 * Collections.unmodifiableNavigableMap</li>
 * <li>
 * Collections.unmodifiableNavigableSet</li>
 * <li>
 * Collections.unmodifiableSet</li>
 * <li>
 * Collections.unmodifiableSortedMap</li>
 * <li>
 * Collections.unmodifiableSortedSet</li>
 * </ul>
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ImmutableShoppingList {

	private final List<String> list;
	private final List<String> list2;

	public ImmutableShoppingList(List<String> list) {
		this.list = Collections.unmodifiableList(list);
		List<String> tmpListOfHolding = new ArrayList<>();
		tmpListOfHolding.addAll(list);
		this.list2 = Collections.unmodifiableList(tmpListOfHolding);
	}

	/***
	 * Bad news! If you hang onto the reference to the collection when you
	 * create the collection, you can still modify it, even if you store it as
	 * an unmodifiable collection internally
	 * 
	 * Here's an example: <code><br/>
List<String> originalList = new ArrayList<>();<br/>
theList.add("apple");<br/>
ImmutableShoppingList blah = new ImmutableShoppingList(originalList);<br/>
originalList.add("candy bar");<br/>
</code>
	 * 
	 * @return
	 */
	public List<String> getList() {
		return list;
	}

	public String[] getArray() {
		return (String[]) list2.toArray();
	}
}
