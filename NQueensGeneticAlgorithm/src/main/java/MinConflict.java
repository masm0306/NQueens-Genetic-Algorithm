import static java.lang.Math.abs;

public class MinConflict implements IProblem{
    @Override
    public double fitness(AElement s) {
        int result = 0;
        for (int i = 0; i < s.permutation.length; i++)
            result += h(i,s.permutation);
        return result >> 1;
    }

    private int h(int a, Integer[] permutation){
        int result = 0;
        for (int i = 0; i < permutation.length; i++) {
            if(i != a){
                //horizontal and diagonal
                if(permutation[i].equals(permutation[a]) || abs(permutation[i] - permutation[a]) == abs(i - a))
                    result += 1;
            }
        }
        return result;
    }
}
