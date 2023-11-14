import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenotypeMatcher {

    private static final char dominantAlleleCode = 'B', recessiveAlleleCode = 'b';
    private static final int dominantAlleleCount = 100, recessiveAlleleCount = 100;
    private static final List<Character> dominantAlleles = Collections.nCopies(dominantAlleleCount, dominantAlleleCode);
    private static final List<Character> recessiveAlleles = Collections.nCopies(recessiveAlleleCount, recessiveAlleleCode);

    public static void main(String[] args) {
        List<Character> parentPool = new ArrayList<>(dominantAlleles);
        parentPool.addAll(recessiveAlleles);

        generatePairs(parentPool, true);
    }

    public static void generatePairs(List<Character> parentPool, boolean replacement) {
        int homozygousDominant = 0, homozygousRecessive = 0, heterozygous = 0;

        while ((homozygousDominant + homozygousRecessive + heterozygous) <= Math.floor(parentPool.size() / 2D) - 1) {
            String offspring = getRandomPair(parentPool, replacement);

            switch (offspring) {
                case "BB" -> homozygousDominant++;
                case "bb" -> homozygousRecessive++;
                default -> heterozygous++;
            }
        }

        int totalPairs = (homozygousDominant + homozygousRecessive + heterozygous);

        System.out.printf("Total pairs: %d%n", totalPairs);
        System.out.printf("  Homozygous Dominant (%c%c) : %d | %01.2f%n", dominantAlleleCode, dominantAlleleCode, homozygousDominant, (double) homozygousDominant / totalPairs);
        System.out.printf("  Heterozygous (%c%c)        : %d | %01.2f%n", dominantAlleleCode, recessiveAlleleCode, heterozygous, (double) heterozygous / totalPairs);
        System.out.printf("  Homozygous Recessive (%c%c): %d | %01.2f%n", recessiveAlleleCode, recessiveAlleleCode, homozygousRecessive, (double) homozygousRecessive / totalPairs);
    }

    public static String getRandomPair(List<Character> parentPool, boolean replacement) {
        Random random = new Random();
        int randomValue = random.nextInt(parentPool.size());

        char firstAllele = parentPool.get(randomValue);
        if (!replacement) parentPool.remove(randomValue);
        randomValue = random.nextInt(parentPool.size());
        char secondAllele = parentPool.get(randomValue);
        if (!replacement) parentPool.remove(randomValue);

        return String.format("%s%s", firstAllele, secondAllele);
    }
}
