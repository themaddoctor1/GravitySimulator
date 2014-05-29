#GravitySimulator

A 2D Gravity simulator with an input system.

##Description
   This program is built simply to simulate gravity in two dimensions. The interface is designed to allow the user to manually add entities into the simulation. The physics engine is pretty simple in concept. Every object in the simulator, referred to as entities, is gravitated towards the others every time the game cycles. This will change the velocity so that objects will be pulled towards each other.
   Due to the intent to maximize the simplicity of the program and its code, I have used a collision system where two colliding objects will become one if they collide. Their masses, as well as volume and momentum, will combine and will form a single entity.

##Controls
* Left-click.........Place Entity
* Up/Down............Change magnitude of initial velocity
* Left/Right.........Change direction of initial velocity
* Minus..............Lower the setting for current mode(Mass or Radius)
* Equals.............Increase the setting for current mode(Mass or Radius)
* B..................Toggles Barycenter display
* H..................Toggle Help Menu
* M..................Switch to Mass mode
* P..................Pause the simulation
* R..................Switch to Radius Mode


