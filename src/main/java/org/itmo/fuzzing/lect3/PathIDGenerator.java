package org.itmo.fuzzing.lect3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PathIDGenerator {

    public static int getPathID(Set<Location> locations) {
        int hash = 5381; // Начальное значение для алгоритма хеширования DJB2

        // Сортируем локации для обеспечения консистентности хеша
        List<Location> sortedLocations = new ArrayList<>(locations);
        sortedLocations.sort((a, b) -> {
            int fileComp = a.getFilename().compareTo(b.getFilename());
            if (fileComp != 0) return fileComp;
            int lineComp = Integer.compare(a.getLineno(), b.getLineno());
            if (lineComp != 0) return lineComp;
            return a.getFunction().compareTo(b.getFunction());
        });

        for (Location loc : sortedLocations) {
            // Хешируем filename
            for (char c : loc.getFilename().toCharArray()) {
                hash = ((hash << 5) + hash) + c;
            }

            // Хешируем lineno
            hash = ((hash << 5) + hash) + loc.getLineno();

            // Хешируем function
            for (char c : loc.getFunction().toCharArray()) {
                hash = ((hash << 5) + hash) + c;
            }
        }

        return hash;
    }


}
