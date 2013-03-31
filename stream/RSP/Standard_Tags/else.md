#The else tag

>[:else if *condition*]
>[:else]

Specifies the else statement. For example,

    [:if foo.isDirectory]
      <li>[= foo.name] is a directory</li>
    [:else if foo.isFile]
      <li>[= foo.name] is a file.</li>
    [:else]
      <li>[= foo.name] is unknown.</li>
    [/if]

As shown, it must follow a [[:if]](if.md) statement or another else statement.