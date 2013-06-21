#The Tournament Scheme

###It's a stochastic optimization method meant for massive parallelization


To try out the sample (MathParticle), you'll need to compile everything.
 >scalac *.scala

Then you should run the MathParticleTournament class
 >scala MathParticleTournament

---------------------------------------------------------------------------

It should hopefully output "Best: 5 7 2"

You see, it was trying to factor the polynomial
 >x^6 + 2x^5 + 7x + 14

into something of the form
 >(x^a + b)(x + c)

So, when it output "Best: 5 7 2", it really computed
 >x^6 + 2x^5 + 7x + 14 = (x^5 + 7)(x + 2)

Pretty cool, but a trivial demonstration of the power of the Tournament Scheme

---------------------------------------------------------------------------

####The tournament scheme is composed of a couple notions:

+Particle: The abstraction around a particular solution for a problem
+Evaluator: The algorithm which takes a list of particles and decides which ones are good/bad
+Message: A simple case class to wrap up a Particle with added info: Winner or Loser
+Table: An actor that holds evaluation rounds and dispatches evaluations to the tournament
+Tournament: The environment which creates tables of particles and evolves particles


####In order to use the tournament scheme, you gotta subclass the fundamental classes:

+Particle
 *Supply bounds for Particle parameters (currently they have to be Long values, but you can go into Particle and change that)
 *Supply a constructor that calls super(bounds)
+Evaluator
 *Supply grabNext method (currently set up to return Losers as the last particle of a table should win)
+Table
 *Supply constructEvaluator method --> this is just a factory constructor of your particular Evaluator subclass
+Tournament
 *Supply constructRandomParticle method --> this is just a factory constructor of your particular Particle subclass
 *Supply constructTable method --> this is just a factory constructor of your particular Table subclass

Clearly, the bulk of the work is going to be in your Evaluator's grabNext method (choosing winners/losers from a list of Particles)

For the MathParticle example, I made fitness methods in Particle to alleviate the Evaluator's duties.

Though, wrestling with the Scala compiler for correct type parameterization may also take up your time.
----------------------------------------------------------------------------

Finally, you'll want to make a companion object for your Tournament subclass with this method
 >object TourneySubClass{
 > def main(args:Array[String]){
 >  val tournament = new TourneySubclass(a,b,c)
 >  tournament.init
 > }
 >}

+"a" is the number of particles per table
+"b" is the number of tables (concurrent evaluation rounds)
+"c" is the number of generations for particle evolution

Go play around with those numbers and see how they affect the results (you might want to print intermediate results in the Tournament class)
------------------------------------------------------------------------------

#####TODO: Use Tournament Scheme to make Connect4 AI