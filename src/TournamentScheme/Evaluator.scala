package TournamentScheme

abstract class Evaluator[SubParticle <: Particle](particles:List[SubParticle]) {

  def next = if(particles.size == 1) Winner(particles.head) else grabNext
  def grabNext:Message

}