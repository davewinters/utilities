import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.*;

public class JMXAccess {
    public static void main(String[] args) throws Exception {
        String host = "localhost";  // Your JBoss Bind Address default is localhost
        int port = 4447;  // JBoss remoting port

        File file = new File("/home/dave/monitoringdata.txt");
        
        if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		

        String urlString ="service:jmx:remoting-jmx://" + host + ":" + port;
        System.out.println("		\n\n\t****  urlString: "+urlString);

        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, null);
        MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

        // object name retrieved using jconsole or other tool
        ObjectName objectName=new ObjectName("jboss.as:subsystem=jca,workmanager=default,long-running-threads=default-long-running-threads");
        
     
        Integer currthreadcount=(Integer)connection.getAttribute(objectName, "currentThreadCount");
        System.out.println("the current thread count is" + currthreadcount);
        
        bw.write("the current thread count is" + currthreadcount);
        bw.close();
        
        // other mbean attribute values

        jmxConnector.close();
    }
}

