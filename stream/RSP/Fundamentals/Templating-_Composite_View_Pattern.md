#Templating: Composite View Pattern

The pages in a website usually share similar structure. Each page is made of different independent pieces, but placed in the same layout across the website.

In this section, we describe how to define the shared layout (aka., the template), define page fragments and assemble them into a complete page. It is based on the so-called *Composite View* pattern.

##Template

In Rikulo Stream, a template is a RSP page. For example, here is the template we'd like to have:

![Composite View](composite-view.jpg?raw=true)

You can create a RSP page as the template:

    [page args="header, sidebar, body, footer"]

> For easy understanding, we implement the layout with `TABLE`. However, it is suggested to `DIV` with proper CSS instead, for better performance and flexibility.