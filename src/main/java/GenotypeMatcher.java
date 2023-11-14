import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenotypeMatcher {

    private static final char dominantAlleleCode = 'B', recessiveAlleleCode = 'b';
    private static final int dominantAlleleCount = 100, recessiveAlleleCount = 100;
    private static final List<Character> dominantAlleles = Collections.nCopies(dominantAlleleCount, dominantAlleleCode);
    private static final List<Character> recessiveAlleles = Collections.nCopies(recessiveAlleleCount, recessiveAlleleCode);
    private static final int totalOffspring = 100;
    private static final boolean replacement = true;

    public static void main(String[] args) {
        List<Character> parentPool = new ArrayList<>(dominantAlleles);
        parentPool.addAll(recessiveAlleles);
        int homozygousDominant = 0, homozygousRecessive = 0, heterozygous = 0;

        while ((homozygousDominant + homozygousRecessive + heterozygous) < totalOffspring) {
            String offspring = getRandomPair(parentPool);

            switch (offspring) {
                case "BB" -> homozygousDominant++;
                case "bb" -> homozygousRecessive++;
                default -> heterozygous++;
            }
        }

        System.out.printf("Total pairs: %d%n", (homozygousDominant + homozygousRecessive + heterozygous));
        System.out.printf("  Homozygous Dominant (%c%c) : %d%n", dominantAlleleCode, dominantAlleleCode, homozygousDominant);
        System.out.printf("  Homozygous Recessive (%c%c): %d%n", recessiveAlleleCode, recessiveAlleleCode, homozygousRecessive);
        System.out.printf("  Heterozygous (%c%c)        : %d%n", dominantAlleleCode, recessiveAlleleCode, heterozygous);
    }

    public static String getRandomPair(List<Character> parentPool) {
        Random random = new Random();

        char firstAllele = parentPool.get(random.nextInt(parentPool.size()));
        char secondAllele = parentPool.get(random.nextInt(parentPool.size()));

        return String.format("%s%s", firstAllele, secondAllele);
    }
}
