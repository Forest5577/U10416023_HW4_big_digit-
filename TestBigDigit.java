//U10416023

//Import the api we need
import static java.lang.System.out;

//Main class
public class TestBigDigit {

	//Main method
	public static void main(String[] args) {

		//Create an object named object 1
		BigDigit object1 = new BigDigit("99999999999999999999999901202020000000000000000000000000000000000000000");

		//Create an object named object 2
		BigDigit object2 = new BigDigit("-2");

		//Do the addition with object2
		out.println(object1.add(object2));    //addition

		//Do the subtraction with object2
		out.println(object1.subtract(object2));  // subtraction
	}
}
