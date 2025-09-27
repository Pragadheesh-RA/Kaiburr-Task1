package com.kaiburr.taskmanager.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CommandValidator {

    // List of unsafe substrings in commands
    private static final List<String> UNSAFE_COMMANDS = Arrays.asList("rm", "sudo", "vi", "dd", "shutdown");

    // Returns false if command contains any unsafe keywords
    public boolean isSafe(String command) {
        if (command == null) return false;
        String lowerCmd = command.toLowerCase();
        for (String unsafe : UNSAFE_COMMANDS) {
            if (lowerCmd.contains(unsafe)) return false;
        }
        return true;
    }
}
