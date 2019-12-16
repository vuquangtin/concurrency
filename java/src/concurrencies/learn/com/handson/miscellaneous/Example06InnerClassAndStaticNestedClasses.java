/**
 * 
 */
package com.handson.miscellaneous;

/**
 * @author sveera
 *
 */
public class Example06InnerClassAndStaticNestedClasses {

	/* Example on inner class and static nested class */
	public static void main(String[] args) {
		OuterClass outerClass = new OuterClass("OuterClassInstanceVariable");
		OuterClass.InnerClass innerClass = outerClass.new InnerClass("InnerClassInstanceVariable");
		OuterClass.InnerStaticClass innerStaticClass = new OuterClass.InnerStaticClass(
				"InnerStaticClassInstanceVariable");

		System.out.println(outerClass.getInfo());
		System.out.println(innerClass.getInfo());
		System.out.println(innerStaticClass.getInfo());
	}

	private static class OuterClass {

		private static String outerClassStaticVariable = "OuterClassStaticVariable";
		private final String instanceVariable;

		public OuterClass(String instanceVariable) {
			super();
			this.instanceVariable = instanceVariable;
		}

		public String getInfo() {
			return String.format("This is from %s.getInfo() and this class has access to variables %s , %s",
					this.getClass().getSimpleName(), outerClassStaticVariable, instanceVariable);
		}

		public class InnerClass {
			private final String instanceVariable;

			public InnerClass(String instanceVariable) {
				super();
				this.instanceVariable = instanceVariable;
			}

			public String getInfo() {
				return String.format(
						"This is from %s.getInfo() and this class has access to variables %s using %s , %s using %s , %s using %s ",
						this.getClass().getSimpleName(), OuterClass.outerClassStaticVariable,
						"OuterClass.outerClassStaticVariable", OuterClass.this.instanceVariable,
						"OuterClass.this.instanceVariable", this.instanceVariable, "this.instanceVariable");
			}

		}

		public static class InnerStaticClass {
			private final String instanceVariable;

			public InnerStaticClass(String instanceVariable) {
				super();
				this.instanceVariable = instanceVariable;
			}

			public String getInfo() {
				return String.format(
						"This is from %s.getInfo() and this class has access to variables %s using %s , %s using %s ",
						this.getClass().getSimpleName(), OuterClass.outerClassStaticVariable,
						"OuterClass.outerClassStaticVariable", this.instanceVariable, "this.instanceVariable");
			}

		}

	}

}
