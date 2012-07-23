#Fundamentals of Effects

Effects are utilities for playing animations against views and DOM elements.

##Motion

[Motion](api:effect) is a controller that controls how an animation progresses over time. Instead of manipulate the animated object directly, it executes the given closures at each animation frame. It provides the life cycle concept of start, move, end, pause, and resume, with corresponding callbacks and control API.

Instead of instantiating [Motion](api:effect), you can instantiate the specialized [EasingMotion](api:effect) and [LinearPositionMotion](api:effect) to work with partially defined motion constructions.