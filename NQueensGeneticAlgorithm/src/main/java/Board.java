import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends AElement{

    public Board(Integer[] permutation,IProblem fitAlgorithm){
        super(permutation,fitAlgorithm);
    }


    public Board(int n, IProblem fitAlgorithm){
        super(n,fitAlgorithm);
    }




    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < permutation.length; i++) {
            for (int j = 0; j < permutation.length; j++) {
                if(i == permutation[j])
                    result.append(" Q ");
                else
                    result.append(" - ");
                if(j == permutation.length - 1)
                    result.append("\n");
            }
        }
        return result.toString();
    }

    private void copyToOffspring(int i, int start, Integer[] parent, Integer[] offspring, boolean[] bin){
        if(bin[parent[i]]){
            for (int j = start; j < parent.length; j++) {
                if(!bin[parent[j]]) {
                    offspring[i] = parent[j];
                    bin[parent[j]] = true;
                    break;
                }
            }
        }
        else {
            offspring[i] = parent[i];
            bin[parent[i]] = true;
        }

    }

    @Override
    List<AElement> crossover(AElement e) {
        Random r = new Random();
        Integer[] parent1 = this.permutation;
        Integer[] parent2 = e.permutation;

        // Choose two crossover points
        int crossoverPoint1 = r.nextInt(0, parent1.length);
        int crossoverPoint2 = r.nextInt(0, parent1.length);

        // Make sure crossoverPoint1 is the smaller
        if (crossoverPoint1 > crossoverPoint2) {
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        // offspring
        Integer[] offspring1 = new Integer[parent1.length];
        Integer[] offspring2 = new Integer[parent1.length];
        boolean[] bin1 = new boolean[parent1.length+1];
        boolean[] bin2 = new boolean[parent1.length+1];


        for (int i = crossoverPoint1; i <= crossoverPoint2; i++) {
            int p1 = parent2[i];
            int p2 = parent1[i];
            bin1[p1] = true;
            bin2[p2] = true;
            offspring1[i] = p1;
            offspring2[i] = p2;
        }

        for (int i = 0; i < crossoverPoint1; i++) {
            copyToOffspring(i,i,parent1,offspring1,bin1);
            copyToOffspring(i,i,parent2,offspring2,bin2);
        }

        for (int i = crossoverPoint2+1; i < offspring1.length; i++) {
            copyToOffspring(i,0,parent1,offspring1,bin1);
            copyToOffspring(i,0,parent2,offspring2,bin2);
        }
        List<AElement> result = new ArrayList<>();
        result.add(new Board(offspring1,this.fitAlgorithm));
        result.add(new Board(offspring2,this.fitAlgorithm));
        return result;
    }

    @Override
    public AElement mutation(double pm) {
        Random r = new Random();
        if(r.nextDouble() < pm){
            int i = r.nextInt(0,permutation.length);
            int j = r.nextInt(0,permutation.length);
            Integer temp = this.permutation[i];
            this.permutation[i] = this.permutation[j];
            this.permutation[j] = temp;
        }
        return this;
    }

    @Override
    public int fitness() {
        return (int) this.fitAlgorithm.fitness(this);
    }

    @Override
    int compareTo(AElement e) {
        if(this.fitness == e.fitness) return 0;
        return this.fitness < e.fitness ? 1 : -1;
    }
}
