package TournamentScheme

import scala.actors.Actor
import scala.actors.Actor._

abstract class Table[SubParticle <: Particle](particles:List[SubParticle], masterAddr:Actor) extends Actor{
	
	def constructEvaluator(ps:List[SubParticle]):Evaluator[SubParticle]
  
    def act(){
      var remParticles = particles
      while(!remParticles.isEmpty){
        val evaluator = constructEvaluator(remParticles)
        val next = evaluator.next
        next match {
          case Winner(winner) => winner.addWin()
        		  				 remParticles = remParticles filter (_ != winner)
          case Loser(loser) => remParticles = remParticles filter(_ != loser)
        }
        masterAddr ! next
      }
    }
    
    
}