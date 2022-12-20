import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class Population{
    public List<AElement> p;
    public int size;
    public IProblem fitAlgorithm;

    public Population(List<AElement> p, IProblem fitAlgorithm){
        this.fitAlgorithm = fitAlgorithm;
        this.p = p;
        this.size = p.size();
    }

    public Population(int n, int l, List<AElement> elements, IProblem fitAlgorithm){
        this.fitAlgorithm = fitAlgorithm;
        this.p = elements;
        this.size = p.size();
    }


    private int map(double d, int a, int b){
        return  a + (int) round(d * (b - a));
    }


    public List<AElement> tournamentWithoutReplacement(int n, int s){
        List<AElement> result = new ArrayList<>();

        for (int i = 0; i < s; i++) {
            AElement[] current = permutation(this.size);

            //Tournaments
            for (int j = 0; j < n; j+=s) {
                // Get tournament elements
                List<AElement> tournamentElements = new ArrayList<>(Arrays.asList(current).subList(j, j + s));
                // Tournament result
                result.add(maxElement(tournamentElements));

            }
        }
        return result;
    }


    private AElement[] permutation(int n){
        // New population copy for permutation
        AElement[] population = this.p.toArray(new AElement[this.size]);

        AElement[] copy = new AElement[this.size];
        Random r = new Random();
        for (int i = 0; i < this.size; i++) {
            copy[i] = new Board(population[i].permutation,population[i].fitAlgorithm);
        }


        for(int i=0;i<n-1;i++) {
            // Permutation
            int random = map(r.nextDouble(),i,n-1);
            AElement temp = copy[i];
            copy[i] = copy[random];
            copy[random] = temp;
        }

        return copy;
    }

    private AElement maxElement(List<AElement> es){
        AElement max = es.get(0);
        for (AElement e:es) {
            if(e.compareTo(max) == 1)
                max = e;
        }
        return max;
    }
}
