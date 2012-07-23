#Fundamentals of Effects

Effects are utilities for playing animations against views and DOM elements.

##Motion

[Motion](api:effect) is a controller that controls how an animation progresses over time by manipulating its acceleration. Instead of manipulate the animated object directly, it calls back the given closures at each animation frame.

Instead of instantiating [Motion](api:effect), you can instantiate [EasingMotion](api:effect) and [LinearMotion](api:effect) to have better motion effects.