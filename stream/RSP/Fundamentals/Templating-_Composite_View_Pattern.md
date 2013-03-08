#Templating: Composite View Pattern

The pages in a website usually share similar structure. Each page is made of different independent pieces, but placed in the same layout across the website.

In this section, we describe how to define the shared layout (aka., the template), define page fragments and assemble them into a complete page. It is based on the so-called *Composite View* pattern.

##Create a Template

In Rikulo Stream, a template is a RSP page. For example, here is the template we'd like to have:

![Composite View](composite-view.jpg?raw=true)

You can create a RSP page as the template:

    [!-- classic.rsp.html --]
    [page args="header, sidebar, body, footer"]
    <div>
      <div class="header">
        [=header]
      </div>
      <div class="sidebar">
        [=sidebar]
      </div>
      <div class="body">
        [=body]
      </div>
      <div class="footer">
        [=footer]
      </div>
    </div>

As shown, the `args` attribute of the [page](../Standard_Tags/page.md) tag defines the names of fragments (aka., the insert points or *gaps*). Then, you can reference them in the locations you'd like with the [=](../Standard_Tags/=.md) tag.

##Create the Composited Page

A composited page is the page composited by use of one or more templates and page fragments. For example, we can create a home page as follows:

    [!-- home.rsp.html --]
    <!DOCTYPE html>
    <html>
      <head>
        <title>Stream: Hello Templating</title>
        <link href="theme.css" rel="stylesheet" type="text/css" />
      </head>
      <body>
        [include classic]
          [var header]
            [include "/header.html"/]
          [/var]

          [var sidebar]
            [include sidebar/]
          [/var]

          [var footer]
            [include "/footer.html"/]
          [/var]

          [var body]
      <h1>Hello Templating</h1>
      <p>In this example, we demostrate how to define the shared layout (aka., the template), define page ...</p>
          [/var]
        [/include]
      </body>
    </html>

As shown, we include the template by use of the [include](../Standard_Tags/include.md) tag. Then, we use the [var](../Standard_Tags/page.md) tag to define the content of a page fragment that will be inserted into the template.

For a runnable example, you can refer to the [hello-templating](source:example) example.

##More About Templates

As you might suggest, a composited page can use any number of templates. A template can be a composited page too.

In additions, you can design a template to be a complete web page and let the composited page to replace only the fragments that need to be changed. For example,

    [!-- classic.rsp.html --]
    [page args="title, header, sidebar, body, footer"]
    <!DOCTYPE html>
    <html>
      <head>
        <title>[=title]</title>
        <link href="theme.css" rel="stylesheet" type="text/css" />
      </head>
      <body>
        <div>
          <div class="header">
          [if header != null]
            [=header]
          [else]
            [include "/header.html"/]
          [/if]
          </div>

          <div class="sidebar">
          [if sidebar != null]
            [=sidebar]
          [else]
            [include sidebar/]
          [/if]
          </div>

          <div class="body">
            [=body]
          </div>

          <div class="footer">
          [if footer != null]
            [=footer]
          [else]
            [include "/footer.html"/]
          [/if]
          </div>
        </div>
      </body>
    </html>

Then, the composited page can be much simpler:

    [!-- home.rsp.html --]
    [include classic title="Stream: Hello Templating"]
      [var body]
      <h1>Hello Templating</h1>
      <p>In this example, we demostrate how to define the shared layout (aka., the template), define page ...</p>
      [/var]
    [/include]
