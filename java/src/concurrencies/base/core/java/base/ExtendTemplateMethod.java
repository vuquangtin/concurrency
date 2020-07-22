package core.java.base;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/frameworks">https://github.com/

 *      vuquangtin/frameworks</a>
 *
 */
public abstract class ExtendTemplateMethod extends TemplateMethod {
	protected static Logger logger = Logger
			.getLogger(ExtendTemplateMethod.class.getName());

	@Override
	public void runTemplateMethod(String[] args, ITemplateMethod iTemplateMethod) {
		super.runTemplateMethod(args, iTemplateMethod);
	}

	@Override
	public void runTemplateMethod(String[] args,
			IExtendTemplateMethod iTemplateMethod) {
		super.runTemplateMethod(args, iTemplateMethod);
	}

	@Override
	public void runTemplateMethod(String[] args) {
		runTemplateMethod(args, true);
	}

	public void runTemplateMethod(String[] args, boolean isLog) {
		super.runTemplateMethod(args);
		printDot("implementionEleven");
		try {
			implementionEleven(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionTwelve");
		try {
			implementionTwelve(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionThirteen");
		try {
			implementionThirteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionFourteen");
		try {
			implementionFourteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionFifteen");
		try {
			implementionFifteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionSixteen");
		try {
			implementionSixteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionSeventeen");
		try {
			implementionSeventeen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionEighteen");
		try {
			implementionEighteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionNineteen");
		try {
			implementionNineteen(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}
		printDot("implementionTwenty");
		try {
			implementionTwenty(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
			log(t);
		}

	}

	public abstract void implementionEleven(String[] args) throws Exception;

	public abstract void implementionTwelve(String[] args) throws Exception;

	public abstract void implementionThirteen(String[] args) throws Exception;

	public abstract void implementionFourteen(String[] args) throws Exception;

	public abstract void implementionFifteen(String[] args) throws Exception;

	public abstract void implementionSixteen(String[] args) throws Exception;

	public abstract void implementionSeventeen(String[] args) throws Exception;

	public abstract void implementionEighteen(String[] args) throws Exception;

	public abstract void implementionNineteen(String[] args) throws Exception;

	public abstract void implementionTwenty(String[] args) throws Exception;

}
