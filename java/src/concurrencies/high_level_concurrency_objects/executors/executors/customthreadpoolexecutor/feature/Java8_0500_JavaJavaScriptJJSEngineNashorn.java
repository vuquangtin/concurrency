package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Java8_0500_JavaJavaScriptJJSEngineNashorn {
	
	public static void main(String args[]) throws ScriptException {
		
		new Java8_0500_JavaJavaScriptJJSEngineNashorn().nashornJavaJavaScriptJJSEngine();
		
		new Java8_0500_JavaJavaScriptJJSEngineNashorn().nashornJavaScriptEngine();
		
	}
	
	
	void nashornJavaJavaScriptJJSEngine(){
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		String name = "Mahesh";
		Integer result = null;
		try {
			nashorn.eval("print('" + name + "')");
			result = (Integer) nashorn.eval("10 + 2");
		} catch (ScriptException e) {
			out.println("Error executing script: " + e.getMessage());
		}
		out.println(result.toString());
	}
	
	
	void nashornJavaScriptEngine() throws ScriptException{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName( "JavaScript" );
		out.println( engine.getClass().getName() );
		out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
	}
	
	
}
