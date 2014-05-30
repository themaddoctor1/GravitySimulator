#GravitySimulator

A 2D Gravity simulator with an input system.

##Description
        This program is built simply to simulate gravity in two dimensions. The interface is designed
    to allow the user to manually add entities into the simulation. The physics engine is pretty
    simple in concept. Every object in the simulator, referred to as entities, is gravitated 
    towards the others every time the game cycles. This will change the velocity so that objects
    will be pulled towards each other.
        Due to the intent to maximize the simplicity of the program and its code, I have used a collision
    system where two colliding objects will become one if they collide. Their masses, as well as 
    volume and momentum, will combine and will form a single entity.

##How it Works
        As far as it goes for me, the code is very simple. The program is basically a cycler program, 
    which means that it repeats a process over and over again. In this simulator, it repeatedly 
    enacts the gravitational forces, the movement, and the collisions.
        In the simulator, I use Newtonian Gravitation to calculate the force of gravity on objects.
    Just like in normal physics, the equation for gravitational force is still Gmm/r^2. But, I
    modified the equation so that it would enact the force over several cycles. To put it simply, the
    game runs at 200 cycles per second, or 200 Hz, if you'd like to see it that way. So, each time 
    the game cycles, I use 1/200th of the force to modify the velocity. For example, in a single
    cycle, if the acceleration should be 1 m/s, the acceleration will be 0.005 m/s in that single
    cycle because (1 m/s) / 200 = 0.005 m/s. Over 200 cycles, or 1 second, the acceleration will
    equal 1 m/s.
        The system is similar for movement. In the simulator, every object will have a speed.
    Because there are 200 cycles per second, or 200 Hz, only 1/200th of the speed will be enacted
    on the coordinate. To demonstrate, consider an object moving at +10 m/s on the x-axis. In the
    code, the x-value will increase by 10 every second. After a single cycle, the x-coordinate at
    this speed will go up by 0.05, meaning that it will travel 0.05 m because (10 m/s) / 200 = 0.05 m/s.
    Obviously, over a single second, this will total out to 10 m/s.
        Collision physics, on the other hand, requires a different explanation. In the simulator,
    collision are inelastic. The way I designed it, their momentums will collide, and then the
    will combine their mass AND their volume. In the code, they are calculated as if they were
    spheres, even though they are drawn as circles in the game. When the combination occurs,
    their masses are used to calculate a combined coordinate and mass. Then, their volumes are combined
    and a new radius is determined. In the end, two colliding entities will become one.
        Besides this, there are several components that aren't worth describing in detail.
    Whenever the game is paused, the game will simply do none of this stuff. It will just sit there
    and wait until it is unpaused. But, entities can be added to the game, allowing one to place
    objects in exact positions at exact velocities at a given time. Unfortunately, the objects can't
    currently be removed from the simulator, but they can be removed by using a really fast object
    to add to its momentum and send it flying out of the map.
    
   

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


