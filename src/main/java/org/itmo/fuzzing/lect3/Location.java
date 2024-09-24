package org.itmo.fuzzing.lect3;

public class Location {
    private String filename;
    private int lineno;
    private String function;

    public Location(String filename, int lineno, String function) {
        this.filename = filename;
        this.lineno = lineno;
        this.function = function;
    }

    public String getFilename() {
        return filename;
    }

    public int getLineno() {
        return lineno;
    }

    public String getFunction() {
        return function;
    }

    public static Location buildFromString(String coverage) {
        String filename = "";
        String function = coverage.split(":")[0];
        String lineNumber = coverage.split(":")[1];
        return new Location(filename, Integer.parseInt(lineNumber), function);
    }

    public String covertToString() {
        return filename + ":" + lineno;
    }

    @Override
    public String toString() {
        return "Location{filename='" + filename + "', lineno=" + lineno + ", function='" + function + "'}";
    }
}
