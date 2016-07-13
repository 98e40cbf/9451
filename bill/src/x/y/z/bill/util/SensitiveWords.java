package x.y.z.bill.util;

import io.alpha.core.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SensitiveWords {

    private static List<String> words;
    static {
        try {
            words = Files
                    .readAllLines(Paths.get(ResourceUtils.getResources("classpath:sensitiveWords.txt")[0].getURI()));
        } catch (Exception e) {
        }
        if (words == null) {
            words = new ArrayList<>(0);
        }
    }

    public static boolean exists(final String word) {
        return words.stream().anyMatch(w -> StringUtils.containsIgnoreCase(word, w));
    }

}
