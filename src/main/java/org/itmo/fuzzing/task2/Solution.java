package org.itmo.fuzzing.task2;

import java.util.ArrayDeque;

public class Solution {
    enum STATE {
        COLLECTING_NAME,
        WAIT_NAME,
    }

    public static String simplifyPath(String path) {
        if (path.isEmpty() || path.charAt(0) != '/' || path.length() > 3000 || !path.matches("[_./a-zA-Z\\d]+"))
            throw new IllegalArgumentException();
        var stack = new ArrayDeque<String>();
        var state = STATE.WAIT_NAME;
        var buffer = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                if (state == STATE.WAIT_NAME) continue;
                else {
                    state = STATE.WAIT_NAME;
                    var res = buffer.toString();
                    if (res.equals("..")) {
                        stack.pollLast();
                    } else if (!res.equals(".")) {
                        stack.add(res);
                    }
                    buffer = new StringBuilder();
                }
            } else {
                if (state == STATE.WAIT_NAME) {
                    state = STATE.COLLECTING_NAME;
                }
                buffer.append(path.charAt(i));
            }

        }
        var last = buffer.toString();
        if (last.equals("..")) {
            stack.pollLast();
        } else if (!last.isEmpty() && !last.equals(".")) {
            stack.add(last);
        }
        return "/" + String.join("/", stack);
    }
}

