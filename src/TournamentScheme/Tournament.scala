package TournamentScheme

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.mutable.Queue

abstract class Tournament[SubParticle <: Particle](nPartsPerTable:Int, nTables:Int, nGenerations:Int) extends Actor{
	val goodParticles = new Queue[SubParticle]()
	val badParticles = new Queue[SubParticle]()
	var bestParticle:SubParticle = null.asInstanceOf[SubParticle]
    var generationCount = 0
    var activeTables = 0
    
    def constructRandomParticle:SubParticle
    def constructTable(ps:List[SubParticle], reference:Actor):Table[SubParticle]
	private def generateTable(ps:List[SubParticle]) = constructTable(ps, self)
	
    def init(){
      for(i <- 1 to nPartsPerTable*nTables){
        goodParticles enqueue (constructRandomParticle)
      }
      start()
    }
	
	def act(){
	  while(generationCount < nGenerations){
	    while(notFoundWinner || tablesInProcess){
        	while(enoughPlayersForTable){
        		val nextParticles = pullNextPlayers(nPartsPerTable)
        		fireOffTable(nextParticles)
        	}
        	while(!enoughPlayersForTable && noPlayersComing){
        	    val nextParticles = pullNextPlayers(goodParticles.size)
        	    fireOffTable(nextParticles)
        	}
        	
        	receive{
        	  case Loser(loser) => badParticles enqueue loser.asInstanceOf[SubParticle]
        	  case Winner(winner) => goodParticles enqueue winner.asInstanceOf[SubParticle]
        	  						 activeTables -= 1
        	}
	    }
	    
	    bestParticle = goodParticles.head
        moveBadParticlesToGood()
        generationCount += 1
        if(generationCount < nGenerations) updateAllGoodParticles()
	  }
      println("Best: " + bestParticle)
	}

	
	def tablesInProcess = activeTables != 0
	def noPlayersComing = !tablesInProcess
	def notFoundWinner = goodParticles.size != 1
	def enoughPlayersForTable = goodParticles.size >= nPartsPerTable
	
	
	def pullNextPlayers(n:Int) = (for(i <- 1 to n) yield (goodParticles dequeue)).toList
	
	def fireOffTable(ps:List[SubParticle]){
	  val nextTable = generateTable(ps)
	  activeTables += 1
	  nextTable.start()
	}

	def moveBadParticlesToGood(){
      while(badParticles.size > 0)
        goodParticles enqueue (badParticles dequeue)
    }
	
	def updateAllGoodParticles() = goodParticles foreach (_.updateBy(bestParticle))
	
}