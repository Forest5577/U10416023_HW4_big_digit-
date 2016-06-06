//U10416023

//Import the api we need
import java.util.*;

//Subclass
public class BigDigit {

	//Create a List<Integer> named value
	private List<Integer> value;

	//Constructor
	public BigDigit(String val) {
		// load big number into this java class by constructor
		String v = val.charAt(0) == '-' ? val.substring(1) : val;
		//take out the "-" in the string v
		//put 4 number in an unit
		value = new ArrayList<>();
		for(int i = v.length() - 4; i > -4; i -= 4) {
			value.add(Integer.parseInt(v.substring(i >= 0 ? i : 0, i + 4)));
		}
		//set 8 number as an unit
		int valueLength = (value.size() / 8 + 1) * 8;
		for(int i = value.size(); i < valueLength; i++) {
			value.add(0);
			// set 0 in front of the big number
		}
		//complement  big number by complement
		value = val.charAt(0) == '-' ? toComplement(value) : value;
	}

	//Constructor copy the value
	private BigDigit(List<Integer> value) {
		this.value = value;
	}

	//Constructor take the that value into method
	public BigDigit add(BigDigit that) {
		if(isNegative(that.value)) {
			return subtract(new BigDigit(toComplement(that.value)));
		}
		// set two nember with the same chars
		int length = Math.max(value.size(), that.value.size());
		List<Integer> op1 = copyOf(value, length);
		List<Integer> op2 = copyOf(that.value, length);
		List<Integer> result = new ArrayList<>();

		int carry = 0;
		for(int i = 0; i < length - 1; i++) {
			int c = op1.get(i) + op2.get(i) + carry;
			if(c < 10000) {
				carry = 0;
			} else {
				c -= 10000;
				carry = 1;
			}
			result.add(c);
		}

		if(carry == 1) { // deal with overflow
			if(isPositive(op1)) { result.add(1); }
			else { result.clear(); } // the overflow of number addition is 0
			for(int i = 0; i < 8; i++) { result.add(0); } // add eight number automatically
		} else { //complement    positive number add  0
			result.add(isPositive(op1) ? 0 : 9999);
		}
		return new BigDigit(result);
	}

	public BigDigit subtract(BigDigit that) {
		if(isNegative(that.value)) {
			return add(new BigDigit(toComplement(that.value)));
		}
		// set numbers as the same digit
		int length = Math.max(value.size(), that.value.size());
		List<Integer> op1 = copyOf(value, length);
		List<Integer> op2 = copyOf(that.value, length);
		List<Integer> result = new ArrayList<>();

		int borrow = 0;
		for(int i = 0; i < length - 1; i++) {
			int c = op1.get(i) - op2.get(i) - borrow;
			if(c > -1) {
				borrow = 0;
			} else { //get the number from the upper digit
				c += 10000;
				borrow = 1;
			}
			result.add(c);
		}

		if(borrow == 1) { // deal with the overflow
			if(isNegative(op1)) { result.add(9998); }
			else { result.clear(); } //add negative and positive number together . Finally, it became 0
			for(int i = 0; i < 8; i++) { result.add(9999); } // set 8 digits in the number
		} else {  // complement:negative number add 9999,positive number add 0
			result.add(isNegative(op1) ? 9999 : 0);
		}

		return new BigDigit(result);
	}

	public boolean greaterOrEquals(BigDigit that) {
		return isNegative(subtract(that).value) ? false : true;
	}
	public String toString() {
		// show positive number affirmatively
		List<Integer> v = isNegative(value) ? toComplement(value) : value;
		StringBuilder builder = new StringBuilder();
		for(int i = v.size() - 1; i > -1; i--) {
			builder.append(String.format("%04d", v.get(i)));
		}
		// remove the front 0, add "-" in the front of the number
		while(builder.length() > 0 && builder.charAt(0) == '0') {
			builder.deleteCharAt(0);
		}
		return builder.length() == 0 ? "0" :
			isNegative(value) ? builder.insert(0, '-').toString() :
				builder.toString();
	}
	//complement the number
	private static List<Integer> toComplement(List<Integer> v) {
		List<Integer> comp = new ArrayList<>();
		for(Integer i : v) { comp.add(9999 - i); }
		comp.set(0, comp.get(0) + 1);
		return comp;
	}
	//copy the list
	private static List<Integer> copyOf(
			List<Integer> original, int newLength) {
		List<Integer> v = new ArrayList<>(original);
		for(int i = v.size(); i < newLength; i++) {
			v.add(isPositive(original) ? 0 : 9999);
		}
		return v;
	}

	private static Integer getLast(List<Integer> list) {
		return list.get(list.size() - 1);
	}

	private static boolean isNegative(List<Integer> list) {
		return getLast(list) == 9999;
	}

	private static boolean isPositive(List<Integer> list) {
		return getLast(list) == 0;
	}
}
