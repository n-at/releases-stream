releases-stream
===============

Get email notifications about latest starred GitHub projects releases.

Build
=====

JDK >= 8 required. Build with:

    $ ./mvnw clean package

An executable jar `releases-stream.jar` will be created in `target` directory.

Configure
=========

Application configuration is stored in `application.yml` file. You can use
`application.sample.yml` as an example.

You should provide configuration for:

* Email server and message `from`, `to` and `subject` fields
* GitHub user name and [personal access token](https://help.github.com/articles/creating-an-access-token-for-command-line-use/).

Run
===

Run built executable jar:

    $ ./releases-stream.jar
    
Or with `java`:

    $ java -jar releases-stream.jar

Or with `spring-boot` maven plugin (source code needed):

    $ ./mvnw spring-boot:run

License
=======

BSD, see `LICENSE`.
