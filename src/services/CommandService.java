package services;

import java.io.IOException;

public class CommandService {
    private static final String POWERSHELL_PATH = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe -Command ";

    public static Process runPowershellCommand(String command) throws IOException {
        return Runtime.getRuntime().exec(POWERSHELL_PATH + command);
    }

    public static Process runScript(String scriptPath) throws IOException {
        return Runtime.getRuntime().exec(scriptPath);
    }
}
