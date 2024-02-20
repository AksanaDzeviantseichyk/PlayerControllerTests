package listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.FileWriter;
import java.io.IOException;

public class CustomTestListener extends TestListenerAdapter {
    private static final String LOG_FILE_PATH = "logs/application.log";

    @Override
    public void onTestFailure(ITestResult tr) {
        String methodName = tr.getMethod().getMethodName();
        String className = tr.getTestClass().getName();
        String failureMessage = tr.getThrowable().getMessage();
        String logMessage = String.format("Test %s.%s failed: %s%n", className, methodName, failureMessage);

        writeToLogFile(logMessage);
    }

    private void writeToLogFile(String logMessage) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
