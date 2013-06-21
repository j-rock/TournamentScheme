package MathParticle;

import TournamentScheme.Particle;

public class MathParticle extends Particle
{
    protected static long[][] bounds = { {-20, -20, -20},
                                         { 20,  20,  20}};
    
    public MathParticle()
    {
        super(bounds);
    }

    public double fitness()
    {
        double diffSum = 0.0;
        for(int i=10; i<20; i++)
        {
            double x = i*0.1;
            double valueFunc = secretFunction(x);
            double difference = valueFunc - applyToFunction(x, position);
            diffSum += difference*difference;
        }
        return diffSum;
    }
    
    public double bestFitness()
    {
        double diffSum = 0.0;
        for(int i=0; i<10; i++)
        {
        	double x = i*0.001;
            double valueFunc = secretFunction(x);
            double difference = valueFunc - applyToFunction(x, bestKnownPosition);
            diffSum += difference*difference;
        }
        return diffSum;
    }
    
    public double secretFunction(double x)
    {
        return 14 + 7*x + 2*x*x*x*x*x + x*x*x*x*x*x;
    }
    
    public double applyToFunction(double x, long[] pos)
    {
        return (Math.pow(x,pos[0]) + pos[1])*(x + pos[2]);
    }
    
    public String toString()
    {
        String t = "";
        for(long d : position)
            t += d + " ";
        return t;
    }
}