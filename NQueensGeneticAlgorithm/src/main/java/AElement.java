import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AElement {
    public Integer[] permutation;
    public int fitness;
    public IProblem fitAlgorithm;

    AElement(Integer[] permutation,IProblem fitAlgorithm){
        this.permutation = permutation;
        this.fitAlgorithm = fitAlgorithm;
        this.fitness = this.fitness();
    }

    AElement(int n, IProblem fitAlgorithm){
        this.fitAlgorithm = fitAlgorithm;
        this.permutation = new Integer[n];
        for (int i = 0; i < n; i++) {
            this.permutation[i] = i;
        }
        List<Integer> permutationList = Arrays.asList(this.permutation);
        Collections.shuffle(permutationList);
        this.permutation = permutationList.toArray(new Integer[permutationList.size()]);
        this.fitness = this.fitness();
    }

    abstract List<AElement> crossover(AElement e);
    abstract AElement mutation(double pm);
    abstract int fitness();
    abstract int compareTo(AElement e);
}
