# Knights_tour

[Knights Tour](https://en.wikipedia.org/wiki/Knight%27s_tour)

This started out as a simple program to visualize a knight's tour solution.  It was a simple program where it would run once recursively, then you'd have to restart the whole thing.  I wanted to make it more interactvie and add a menu for 'replayability'.  This meant that I had to rewrite the main algorithm to work in a loop because recursion didn't work nicely with Java Swing...  But it was okay.  

### Controls
Simulate -> Will start a simulation regardless of if one has already been started.  
Clear -> Will stop the current simulation.  
Play/Pause -> Will pause or restart a simulation.  It will not start a new simulation.  
Optimze -> Will toggle an optimization to pick the move with the least amount of following moves.  
Select Side Length -> Will change the board size.  It will only change if a simulation has been cleared.  
Select Simulation Speed -> Will change the speed.  Will take effect during the next simulation or after you resume.  

When a simulation is cleared, you can select a new square to be the starting point by clicking on it.
