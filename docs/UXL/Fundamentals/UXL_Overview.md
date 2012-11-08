#UXL Overview

UXL (User-interface eXtensible language) is a markup language for describing applications' user interfaces. UXL is a simple variant of XML. It allows you to define user interfaces in a similar manner to authoring HTML and XML pages.

> Depending on your preference and your team's skill, you can design UI in Dart, UXL or both.

##How it Works

![How UXL works](how-uxl-works.jpg?raw=true)

##Install

###Install from Dart Pub Repository

Add this to your `pubspec.yaml` (or create it):

    dependencies:
      rikulo_uxl:

###Install from Github for Bleeding Edge Stuff

To install stuff that is still in development, add this to your `pubspec.yam`:

    dependencies:
      rikulo_uxl:
        git: git://github.com/rikulo/rikulo-uxl.git

##Usage

At first, you have to prepare a UXL file defining the user interface. Then, run `uc` (UXL compiler) to compile it into the dart file with [command line interface](http://en.wikipedia.org/wiki/Command-line_interface) as follows:

    dart bin/uc.dart your-uxl-file(s)

A dart file is generated for each UXL file you gave.
