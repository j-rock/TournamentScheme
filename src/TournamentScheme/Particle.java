package TournamentScheme;

public abstract class Particle
{
    protected long[] position;
    protected long[] bestKnownPosition;
    protected long[] velocity;
    
    public Particle(){}

    public Particle(int n, long[][] bounds)
    {
        position = new long[n];
        bestKnownPosition = new long[n];
        velocity = new long[n];

        for(int i=0; i<n; i++)
        {
            position[i] = Particle.random(bounds[0][i], bounds[1][i]);
            bestKnownPosition[i] = position[i];
            velocity[i] = Particle.random(bounds[0][i]-bounds[1][i], bounds[1][i]-bounds[0][i]);
        }
    }
    
    public Particle(Particle p)
    {
    	position = new long[p.position.length];
    	bestKnownPosition = new long[p.position.length];
    	velocity = new long[p.position.length];
    	
    	for(int i=0; i<position.length; i++)
    	{
    		position[i] = p.position[i];
    		bestKnownPosition[i] = p.bestKnownPosition[i];
    		velocity[i] = p.velocity[i];
    	}
    }
    
    public abstract Particle copy();
    
    public void addWin()
    {
        for(int i=0; i<position.length; i++)
            bestKnownPosition[i] = position[i];
    }

    public void updateBy(Particle best)
    {
        double r1 = Math.random();
        double r2 = Math.random();
        double r3 = Math.random();

        for(int i=0; i<velocity.length; i++)
        {
            velocity[i] *= r3;
            velocity[i] += r1*(bestKnownPosition[i] - position[i]) + r2*(best.position[i] - position[i]);
            position[i] += velocity[i];
        }
    }
    

    public void updatePosition()
    {
        for(int i=0; i<position.length; i++)
            position[i] += velocity[i];
    }

    private static long random(long low, long high)
    {
        return (long)(Math.random()*(high-low) + low);
    }

}