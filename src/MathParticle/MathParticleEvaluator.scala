package MathParticle

import TournamentScheme._

class MathParticleEvaluator(particles:List[MathParticle])
				  extends Evaluator[MathParticle](particles){

	def grabNext = {
	  val first::second::rem = particles
	  val loser = if(first.fitness > second.fitness) first else second
	  Loser(loser)
	}
	
}