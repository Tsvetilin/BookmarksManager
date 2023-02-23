package bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.strategy;

import java.util.HashSet;
import java.util.Set;

public class SuffixStrippingStrategy implements StemmingStrategy {
    private static final Set<String> SUFFIX = new HashSet<>();

    static {
        SUFFIX.add("ed");
        SUFFIX.add("ing");
        SUFFIX.add("ly");
    }

    @Override
    public String stem(String word) {

        for (String suffix : SUFFIX) {
            if (word.endsWith(suffix)) {
                return word.substring(0, word.lastIndexOf(suffix));
            }
        }

        return word;
    }
}
