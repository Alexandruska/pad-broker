(ns pad-broker.core
  (:require
    [clojure.tools.logging :as log]
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.edn :as edn]
    [aleph.tcp :as tcp]
    [aleph.netty :as netty]
    [gloss.core :as gloss]))

(def cli-options
  "CLI options for the broker"
  [["-p" "--port PORT" "Port number"
    :default 14141
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-h" "--help"]])

(def broker-protocol
  "
  Define simple binary protocol for broker messages.
  First 4 bytes is a length of the message, and the message is suposed to be
  an `edn` encoded data structure.
  "
 (gloss/compile-frame
   (gloss/finite-frame :uint32
     (gloss/string :utf-8))
   pr-str
   edn/read-string))


(defn message-handler
  "Handles new message for the broker and dispatches it.
  `s` is a duplex stream,
  `info` is a map with connection info"
  [s info]
  (log/info (str "New connection from " info)))

(defn start-server
  "Runs tcp server on the given port.
  Doesn't block the main thread."
  [port]
  (log/info "Broker is up and listening on " port)
  (log/info "Press Ctrl + C to close the application")
  (tcp/start-server message-handler {:port port}))


(defn -main
  "Entry point for the broker.

  Runs the broker on given port (via cli arguments) and blocks the main thread"
  [& args]
  (let [{{port :port help? :help} :options :as opts} (parse-opts args cli-options)]
    (if help?
      (println (:summary opts))
      (netty/wait-for-close (start-server port)))))
