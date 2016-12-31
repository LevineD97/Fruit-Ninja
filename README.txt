=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. I used inheritance / subtyping for my "Fruit" and "Bomb" classes, both
  of which inherited the properties from my superclass "Projectile" (the 
  equivalent of "GameObj" in the Mushroom of Doom). While the fruits and the
  bombs both have similar methods, the bombs instantly cause the game to end
  upon being sliced whereas the fruits increment your score when sliced. Also,
  fruits appear from the bottom of the screen while bombs fall from the top of
  the screen. 

  2. I also incorporated Collections for my fruits and bombs. I used LinkedLists
  to store both types of objects because they are easily resizable and the order
  does not matter (an ArrayList would have been a decent choice as well, but they
  are not as quick when it comes to resizing). Although this technically doesn't
  count in the grading, I figured it would also be noteworthy to mention my use of
  TreeMaps in implementing the user IO for high scores.

  3. I also used IO to record a user's name at the beginning of each round, and at
  the end of each round, the score and username were added to a tree map, and the
  top 5 highscores were printed to the screen. If the user hit a bomb, their score
  would not be counted. If a user failed to hit 6 fruits, the final score would be
  recorded.

  4. Lastly, I implemented JUnit Testing for my Fruit and Bomb classes. This was
  more difficult to implement than expected, but I did write sound test cases.
  This entailed testing whether, after a certain amount of ticks when a fruit or
  a bomb fell off the screen, that they were removed from their respective LinkedLists
  (and the appropriate conditions within the GameCourt changed) and testing whether
  a path between two points delineated by a mouse drag intersected a fruit / bomb at
  a certain position.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

	Projectile is the superclass for the Bomb and Fruit classes, and includes
	all of the methods for a generic flying object.
	
	Fruit is a subclass of Projectile with a constructor that takes the court's
	width and height as parameters and has a unique draw method depending on the
	randomly generated fruit type. It also starts from the bottom of the screen
	and descends with constant acceleration.
	
	FruitType creates an enum that outlines all of Fruit's types.
	
	Bomb is a subclass of Projectile with a constructor that also takes in the
	court's width and height and has a distinct draw method. Bombs spawn at the
	top of the screen and fall with constant acceleration.
	
	GameCourt outlines all of the conditions for the Game, including what happens
	inside tick and paintComponents, similarly to the Mushroom of Doom code.
	
	Game outlines all of the JComponents on the screen and constructs a new game
	court, similarly to the Mushroom of Doom code.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think my design is sound for a number of reasons. I ensured that object's states
were encapsulated by creating getter and setter methods for the objects' states, I
used LinkedLists to store fruits and bombs because of their efficient resizability
and lack of order. 

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

N/A
