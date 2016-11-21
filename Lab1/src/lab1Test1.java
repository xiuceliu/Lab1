import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class lab1Test1 {


	@Test
	public void testDerivative1() {
		lab1 test=new lab1();
		String result=test.derivative("!d/d x");
		assertEquals("1*1*y+2*1",result);
	}
	@Test
	public void testDerivative2() {
		lab1 test=new lab1();
		String result=test.derivative("!d/d y");
		assertEquals("1*x*1+2*x",result);
	}
	
	@Test
	public void testDerivative3() {
		lab1 test=new lab1();
		String result=test.derivative("!d/d a");
		assertEquals("Error",result);
	}
	@Test
	public void testDerivative4() {
		lab1 test=new lab1();
		String result=test.derivative("!d/d ax");
		assertEquals("Error",result);
	}
}
