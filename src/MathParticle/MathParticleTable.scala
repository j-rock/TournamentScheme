package MathParticle

import scala.actors.Actor
import scala.actors.Actor._
import TournamentScheme._

class MathParticleTable(particles:List[MathParticle], masterAddr:Actor) extends
				Table[MathParticle](particles, masterAddr){
				
	def constructEvaluator(ps:List[MathParticle]) = new MathParticleEvaluator(ps)
}