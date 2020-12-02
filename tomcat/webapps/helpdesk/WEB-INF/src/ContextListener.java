import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
	
@WebListener
public class ContextListener implements ServletContextListener {
	private static Map<String, String> contextParameters = new HashMap<>();
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		Enumeration<String> paramNames = context.getInitParameterNames();
		
		while(paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			String value = context.getInitParameter(name);
			contextParameters.put(name, value);
		}
	}
	
	public static String getStringParam(String paramName) {
		return contextParameters.get(paramName);
	}
	public static int getIntParam(String paramName) {
		return Integer.valueOf(contextParameters.get(paramName));
	}
	public static byte[] getByteArrayParam(String paramName) {
		String[] stringParam = contextParameters.get(paramName).split(",");
		int length = stringParam.length;
		byte[] array = new byte[length];
		
		for(int i=0; i<length; i++) {
			array[i] = Byte.valueOf(stringParam[i]);
		}
		return array;
	}	
}
