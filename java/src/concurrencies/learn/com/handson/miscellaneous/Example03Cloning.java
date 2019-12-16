/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example03Cloning {

	/*
	 * Example on cloning. In order to clone any object in java , that class has to
	 * implement Cloneable interface. In order to deep clone of any object ,In the
	 * overridden clone method , You need to clone instance variable's also .Check
	 * out ClonableClass.clone() method in this example. To see
	 * CloneNotSupportedException uncomment the code in this example.
	 */
	public static void main(String[] args) throws CloneNotSupportedException {

		ClonableInstanceVariableClass clonableInstanceVariableClass = new ClonableInstanceVariableClass(
				"ClonableInstanceVariableClass_InstanceVariable");
		NonClonableInstanceVariableClass nonClonableInstanceVariableClass = new NonClonableInstanceVariableClass(
				"NonClonableInstanceVariableClass_InstanceVariable");
		ClonableClass clonableClass = new ClonableClass(clonableInstanceVariableClass,
				nonClonableInstanceVariableClass);
		ClonableClass clonedObject = clonableClass.clone();
		out.println("Hash code of original ClonableClass instance " + clonableClass.hashCode());
		out.println("Hash code of cloned ClonableClass instance " + clonedObject.hashCode());
		out.println("Hash code of original ClonableInstanceVariableClass instance "
				+ clonableInstanceVariableClass.hashCode());
		out.println("Hash code of cloned ClonableInstanceVariableClass instance "
				+ clonedObject.getClonableInstanceVariableClass().hashCode());

		out.println("Hash code of original NonClonableInstanceVariableClass instance "
				+ nonClonableInstanceVariableClass.hashCode());
		out.println("Hash code of cloned NonClonableInstanceVariableClass instance "
				+ clonedObject.getNonClonableInstanceVariableClass().hashCode());
	}

	private static class ClonableClass implements Cloneable {

		private ClonableInstanceVariableClass clonableInstanceVariableClass;
		private NonClonableInstanceVariableClass nonClonableInstanceVariableClass;

		public ClonableClass(ClonableInstanceVariableClass clonableInstanceVariableClass,
				NonClonableInstanceVariableClass nonClonableInstanceVariableClass) {
			super();
			this.clonableInstanceVariableClass = clonableInstanceVariableClass;
			this.nonClonableInstanceVariableClass = nonClonableInstanceVariableClass;
		}

		public ClonableInstanceVariableClass getClonableInstanceVariableClass() {
			return clonableInstanceVariableClass;
		}

		public NonClonableInstanceVariableClass getNonClonableInstanceVariableClass() {
			return nonClonableInstanceVariableClass;
		}

		@Override
		public String toString() {
			return "ClonableClass [clonableInstanceVariableClass=" + getClonableInstanceVariableClass()
					+ ", nonClonableInstanceVariableClass=" + getNonClonableInstanceVariableClass() + "]";
		}

		@Override
		public ClonableClass clone() throws CloneNotSupportedException {
			ClonableClass clonedObject = (ClonableClass) super.clone();
			clonedObject.clonableInstanceVariableClass = this.clonableInstanceVariableClass.clone();
//			clonedObject.nonClonableInstanceVariableClass = this.nonClonableInstanceVariableClass.clone();
			return clonedObject;
		}

	}

	private static class ClonableInstanceVariableClass implements Cloneable {

		private final String instanceVariable;

		public ClonableInstanceVariableClass(String instanceVariable) {
			super();
			this.instanceVariable = instanceVariable;
		}

		public String getInstanceVariable() {
			return instanceVariable;
		}

		@Override
		public String toString() {
			return "ClonableInstanceVariableClass [instanceVariable=" + getInstanceVariable() + "]";
		}

		@Override
		public ClonableInstanceVariableClass clone() throws CloneNotSupportedException {
			return (ClonableInstanceVariableClass) super.clone();
		}
	}

	private static class NonClonableInstanceVariableClass {

		private final String instanceVariable;

		public NonClonableInstanceVariableClass(String instanceVariable) {
			super();
			this.instanceVariable = instanceVariable;
		}

		public String getInstanceVariable() {
			return instanceVariable;
		}

		@Override
		public String toString() {
			return "NonClonableInstanceVariableClass [instanceVariable=" + getInstanceVariable() + "]";
		}

		/*
		 * @Override public NonClonableInstanceVariableClass clone() throws
		 * CloneNotSupportedException { return (NonClonableInstanceVariableClass)
		 * super.clone(); }
		 */
	}

}
