import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaturalSelection {

    private static final char dominantAlleleCode = 'B', recessiveAlleleCode = 'b';
    private static final int dominantAlleleCount = 100, recessiveAlleleCount = 100;
    private static final List<Character> dominantAlleles = Collections.nCopies(dominantAlleleCount, dominantAlleleCode);
    private static final List<Character> recessiveAlleles = Collections.nCopies(recessiveAlleleCount, recessiveAlleleCode);
    private static final int negativeSelectionPercent = 50;
    private static final int totalGenerations = 5;

    public static void main(String[] args) {
        List<Character> parentPool = new ArrayList<>(dominantAlleles);
        parentPool.addAll(recessiveAlleles);
        int generation = 1;

        while (generation <= totalGenerations) {
            parentPool = nextGeneration(parentPool, generation);
            generation++;
        }
    }

    public static List<Character> nextGeneration(List<Character> parentPool, int generation) {
        int homozygousDominant = 0, homozygousRecessive = 0, heterozygous = 0;

        while ((homozygousDominant + homozygousRecessive + heterozygous) <= Math.floor(parentPool.size() / 2D) - 1) {
            String offspring = GenotypeMatcher.getRandomPair(parentPool, true);

            switch (offspring) {
                case "BB" -> homozygousDominant++;
                case "bb" -> homozygousRecessive++;
                default -> heterozygous++;
            }
        }

        // invert and divide negativeSelectionPercent to find survivors
        homozygousRecessive = (int) (homozygousRecessive * ((100 - negativeSelectionPercent) / 100D));

        System.out.printf("==] GENERATION %d [==", generation);
        System.out.printf("%nTotal pairs: %d%n", (homozygousDominant + homozygousRecessive + heterozygous));
        System.out.printf("  Homozygous Dominant (%c%c) : %d%n", dominantAlleleCode, dominantAlleleCode, homozygousDominant);
        System.out.printf("  Heterozygous (%c%c)        : %d%n", dominantAlleleCode, recessiveAlleleCode, heterozygous);
        System.out.printf("  Homozygous Recessive (%c%c): %d%n%n", recessiveAlleleCode, recessiveAlleleCode, homozygousRecessive);

        List<Character> remainingDominantAlleles = Collections.nCopies((homozygousDominant * 2 + heterozygous), dominantAlleleCode);
        List<Character> remainingRecessiveAlleles = Collections.nCopies((homozygousRecessive * 2 + heterozygous), recessiveAlleleCode);
        List<Character> remainingParentPool = new ArrayList<>(remainingDominantAlleles);
        remainingParentPool.addAll(remainingRecessiveAlleles);

        System.out.printf("Remaining Alleles: %d%n", remainingParentPool.size());
        System.out.printf("  Dominant Alleles         : %d%n", remainingDominantAlleles.size());
        System.out.printf("  Recessive Alleles        : %d%n%n", remainingRecessiveAlleles.size());

        GenotypeMatcher.generatePairs(remainingParentPool, true);
        System.out.printf("%n================%n%n");

        return remainingParentPool;
    }
}
