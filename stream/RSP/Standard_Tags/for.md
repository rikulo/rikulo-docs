#The for tag

**Syntax 1**

>[:for *something* in *collection*] *...* [/for]

**Syntax 2**

>[:for *statement1* ; *condition* ; *statement2*] *...* [/for]

Specifies the for statement. For example,

    [:for type in infos.keys]
      <li>[=type]
        <ol>
        [:for name in infos[type]]
          <li>[=name]</li>
        [/for]
        </ol>
      </li>
    [/for]

As shown, it must be closed with `[/for]`.
