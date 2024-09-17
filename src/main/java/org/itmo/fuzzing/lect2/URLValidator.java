package org.itmo.fuzzing.lect2;

import org.itmo.fuzzing.lect1.SimpleFuzz;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class URLValidator {

    public static boolean httpProgram(String url) throws IllegalArgumentException {
        List<String> supportedSchemes = Arrays.asList("http", "https");
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || !supportedSchemes.contains(scheme)) {
                throw new IllegalArgumentException("Scheme must be one of " + supportedSchemes);
            }
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Host must be non-empty");
            }

            // Do something with the URL
            return true;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }

    public static void main(String[] args) {
        SimpleFuzz sf = new SimpleFuzz();
        //Test http
        //Time??

//
//        //Let's try to mutate url
        String seed = "http://www.google.com/search?q=fuzzing";


//        //What happens if we're applying more than one mutation?
//
        MutationFuzzer mf = new MutationFuzzer(Arrays.asList(seed), 5, 10) {
            @Override
            public String mutate(String input) {
                Random random = new Random();
                String seed = input;
                for (int i = 0; i < 100; i++) {
                    int randomInt = random.nextInt(4);
                    switch (randomInt) {
                        case 0:
                            seed = StringMutator.deleteRandomCharacter(seed);
                            break;
                        case 1:
                            seed = StringMutator.insertRandomCharacter(seed);
                            break;
                        default:
                            seed = StringMutator.flipRandomCharacter(seed);
                    }
                }
                return StringMutator.flipRandomCharacter(input);
            }
        };
        System.out.println(mf.fuzz());
        System.out.println(mf.fuzz());
        System.out.println(mf.fuzz());

    }
}