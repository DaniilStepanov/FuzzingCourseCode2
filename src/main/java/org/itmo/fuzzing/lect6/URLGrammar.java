package org.itmo.fuzzing.lect6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLGrammar {

    public static final Map<String, List<String>> URL_GRAMMAR = new HashMap<>() {{
        put("<start>", Arrays.asList("<url>"));
        put("<url>", Arrays.asList("<scheme>://<authority><path><query>"));
        put("<scheme>", Arrays.asList("http", "https", "ftp", "ftps"));
        put("<authority>", Arrays.asList("<host>", "<host>:<port>", "<userinfo>@<host>", "<userinfo>@<host>:<port>"));
        put("<host>", Arrays.asList("www.google.com", "itmo.ru"));
        put("<port>", Arrays.asList("80", "8080", "<nat>"));
        put("<nat>", Arrays.asList("<digit>", "<digit><digit>"));
        put("<digit>", Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        put("<userinfo>", Arrays.asList("user:password"));
        put("<path>", Arrays.asList("", "/", "/<id>"));
        put("<id>", Arrays.asList("abc", "def", "x<digit><digit>"));
        put("<query>", Arrays.asList("", "?<params>"));
        put("<params>", Arrays.asList("<param>", "<param>&<params>"));
        put("<param>", Arrays.asList("<id>=<id>", "<id>=<nat>"));
    }};

}
