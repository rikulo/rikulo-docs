#The dart tag

>[dart]*any dart code*[/dart]

Specifies any dart code to be output to the generated dart file. For example,

    [dart]
    var foo = ["something", "else"];
    [/dart]

The dart code is output directly to the generated dart file. Notice that it is put inside the render function generated, so the variables, if any, are local to the render function.