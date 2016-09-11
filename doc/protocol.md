## Input messages

Places the `payload` to the queue. Queue can be any valid **edn** data.

```clojure
{
    :type :push
    :payload ""
}
```

Pops a message from the queue.

```clojure
{
    :type :pop
    :payload nil
}
```

## Output messages

Response message for all push messages and a pop message when the queue is empty.

```clojure
{
    :type :response
    :error? false
    :msg ""
}
```

Response for a pop message, `payload` contains data from the queue.

```clojure
{
    :type :message
    :payload ""
}
```
