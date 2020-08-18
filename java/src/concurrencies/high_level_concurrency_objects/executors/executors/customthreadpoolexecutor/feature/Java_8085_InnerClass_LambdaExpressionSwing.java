package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Java_8085_InnerClass_LambdaExpressionSwing extends JFrame {

	private static final long serialVersionUID = 1L;

	Java_8085_InnerClass_LambdaExpressionSwing(){
		// assign title to swing alert box
		super("Lambda Expression");
	}

	void actionEventListener() {
		final JButton jButton = new JButton("Start");

		SwingUtilities.invokeLater(() -> {
			setVisible(true);
		});
		// OR
		// setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jButton);

		eventHandlingJava(jButton);
		eventHandlingJava8Lambda(jButton);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
	}
	
	void eventHandlingJava(JButton jButton) {
		String anonymousClassMsg = "\nHandle event using Anonymous Inner class";
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				out.println(anonymousClassMsg);
			}
		});
	}
	
	void eventHandlingJava8Lambda(JButton jButton) {
		String lambdaExpressionMsg = "\nHandle event using Java8 Lambda expression to implement Runnable interface in Swing application"
				+ " and listener code is concise and more readable using lambda expression";
		jButton.addActionListener((event) -> {
			out.println(lambdaExpressionMsg);
		});
	}

	public static void main(String[] args) {
		new Java_8085_InnerClass_LambdaExpressionSwing().actionEventListener();
	}

}
