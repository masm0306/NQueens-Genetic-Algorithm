import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class sGA {
    public Population p;


    public sGA(int n, int l, List<AElement> element, IProblem fitAlgorithm){
        this.p = new Population(n,l,element,fitAlgorithm);
    }

    private String getStatsFitness(int gen){
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", unusualSymbols);
        List<AElement> population = this.p.p;
        double min = population.get(0).fitness;
        double max = population.get(0).fitness;
        double sum = 0;
        for (AElement e:population) {
            sum += e.fitness;
            if(e.fitness < min)
                min = e.fitness;
            else if (e.fitness > max)
                max = e.fitness;
        }
        return gen + ": " + df.format(max) + " " + df.format(sum/population.size()) + " " + df.format(min);
    }


    // ALgorithm Loop
    public AElement oneMaxGeneticAlgorithmLoop(int s, double pc, double pm, int genNumber){
        String firstGen = getStatsFitness(0);
        System.out.println(firstGen);

        for (int i = 1; i <= genNumber; i++){
            this.p = oneMaxGeneticAlgorithm(s,pc,pm);
            if(this.p.size == 1) {
                System.out.println("Solution Found");
                return this.p.p.get(0);
            }
            String genNew = getStatsFitness(i);
            System.out.println(genNew);
        }
        return null;
    }

    // 1 Iteration of the algorithm
    private Population oneMaxGeneticAlgorithm(int s, double pc, double pm){
        int l = this.p.size;
        Random r = new Random();
        List<AElement> winners = this.p.tournamentWithoutReplacement(l,s);
        AElement[] winnersArr = winners.toArray(new AElement[l]);

        List<AElement> newGeneration = new ArrayList<>();
        for (int i = 0; i < l; i+=2) {
            double random = r.nextDouble();

            //Check winner before crossover
            for (int j = 0; j < 2; j++) {
                if(winnersArr[j].fitness == 0) {
                    List<AElement> result = new ArrayList<>();
                    result.add(winnersArr[i]);
                    return new Population(result,this.p.fitAlgorithm);
                }
            }

            if(random < pc){
                List<AElement> crossover = winnersArr[i].crossover(winnersArr[i+1]);
                newGeneration.add(crossover.get(0));
                newGeneration.add(crossover.get(1));
            }
            else{
                newGeneration.add(winnersArr[i]);
                newGeneration.add(winnersArr[i+1]);
            }
        }

        List<AElement> afterBitflip = new ArrayList<>();
        for (AElement e:newGeneration) {
            afterBitflip.add(e.mutation(pm));
        }

        return new Population(afterBitflip,this.p.fitAlgorithm);
    }
}
