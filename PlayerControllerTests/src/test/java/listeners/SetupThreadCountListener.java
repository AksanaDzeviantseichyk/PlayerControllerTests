package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;
import utils.AppConfig;

public class SetupThreadCountListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        String threadCount = AppConfig.getProperty("thread.count");
        if (threadCount == null) {
            throw new IllegalArgumentException("Thread count property not found in config.properties");
        }
        try {
            int count = Integer.parseInt(threadCount);
            XmlSuite xmlSuite = suite.getXmlSuite();
            xmlSuite.setThreadCount(count);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid thread count property value in config.properties");
        }
    }

    @Override
    public void onFinish(ISuite suite) {
    }
}