package MathParticle

import scala.actors.Actor
import scala.actors.Actor._
import TournamentScheme._

class MathParticleTournament(nPartsPerTable:Int, nTables:Int, nGenerations:Int)
			extends Tournament[MathParticle](nPartsPerTable, nTables, nGenerations){

  def constructRandomParticle = new MathParticle()
  def constructTable(ps:List[MathParticle], reference:Actor) = new MathParticleTable(ps, reference)
	
}

object MathParticleTournament{
  def main(args:Array[String]){
    val tourney = new MathParticleTournament(50, 30, 20)
    tourney.init
  }
}