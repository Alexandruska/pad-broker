## PAD labs - Simple Message Broker

This is a demo of a simple message broker implemented in Clojure.

## Prerequisites

- Java is required (JRE)
- Install [Leiningen](https://leiningen.org/#install)
Leiningen is a build and dependency management tool for Clojure.
It's the best tool so far to manage Clojure projects.

## Usage

### Using Leiningen

- Open project's root folder
- Run broker - `lein run` (default port is `14141`, to run on custom port, use `-p <PORT>` option) or `lein run -p <port>`
- Open a REPL (interactive console) using `lein run`
- Create a new client using `client` factory
`(def c (client "localhost" 14141))` (`14141` is the port broker is listening on)
- Send a message to the broker using created client
`(s/put! @c {:type :push :payload "Whatever"})`
and read response from the broker `(s/take! @c)`
- Take a message from the broker
`(s/put! @c {:type :pop :payload nil})`
and read response from the broker `(s/take! @c)`


The broker can be distributed as a standalone jar:

    $ java -jar pad-broker-0.1.0-standalone.jar [args]


## Options

By default the broker listens on the `14141` port. To change that use `-p` or `--port` option.

## Examples


```
pad-broker.core=> (def c (client "localhost" 14141))
#'pad-broker.core/c
pad-broker.core=> (s/put! @c {:type :push :payload "whatever"})
<< true >>
pad-broker.core=> (s/take! @c)
<< {:type :response, :error? false, :message "OK"} >>
pad-broker.core=> (s/put! @c {:type :pop :payload nil})
<< true >>
pad-broker.core=> (s/take! @c)
<< {:type :message, :payload whatever} >>
```

## License

Copyright © 2016 Alex Gavrisco

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
