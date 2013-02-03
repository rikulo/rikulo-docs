#The if tag

>[if *condition*] *...* [/if]

Specifies the if statement. For example,

    [if foo.isDirectory]
      <li>[= foo.name] is a directory</li>
    [else if foo.isFile]
      <li>[= foo.name] is a file.</li>
    [else]
      <li>[= foo.name] is unknown.</li>
    [/if]

As shown, it must be closed with `[/if]`.