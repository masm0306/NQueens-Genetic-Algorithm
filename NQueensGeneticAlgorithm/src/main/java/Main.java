import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random(0);
        MinConflict m = new MinConflict();

//        Integer[] parent1 = {0,1,2,3,4,5,6,7,8};
//        Integer[] parent2 = {8,2,6,7,1,5,4,0,3};
////        Integer[] parent1 = {0,1,2,3};
////        Integer[] parent2 = {2,1,0,3};
//        Board b1 = new Board(parent1,m);
//        Board b2 = new Board(parent2,m);
//        List<AElement> res = b1.crossover(b2);
//        b1.permutation = res.get(0).permutation;
//        b2.permutation = res.get(1).permutation;
//        System.out.println(Arrays.toString(b1.permutation));
//        System.out.println(Arrays.toString(b2.permutation));
//        System.out.println(b1.fitness());
//        System.out.println(b1);
//        System.out.println(b2.fitness());
//        System.out.println(b2);
//
//        Population p = new Population(4,10,m);
//        for (AElement e:p.p) {
//            System.out.println(e);
//        }
        List<AElement> es = new ArrayList<>();
        int n = 100;
        int l = 80;
        int s = 10;
        for (int i = 0; i < l; i++) {
            es.add(new Board(n,m));
        }
        sGA ga = new sGA(n,l,es,m);
        AElement e = ga.oneMaxGeneticAlgorithmLoop(s,0.9,0.3,10000);
        System.out.println(e);
    }
}