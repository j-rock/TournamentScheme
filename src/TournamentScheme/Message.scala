package TournamentScheme

abstract sealed class Message
case class Winner[SubParticle <: Particle](p:SubParticle) extends Message
case class Loser[SubParticle <: Particle](p:SubParticle) extends Message