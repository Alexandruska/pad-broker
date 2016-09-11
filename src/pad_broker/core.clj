(ns pad-broker.core
  (:require
    [aleph.tcp :as tcp]
    [aleph.netty :as netty]
    [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  "CLI options for the broker"
  [["-p" "--port PORT" "Port number"
    :default 14141
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-h" "--help"]])

(defn message-handler
  [s info]
  (println (str "New connection from " info)))

(defn start-server
  [port]
  (println (str "Broker is up and listening on :" port))
  (println "Press Ctrl + C to close the application")
  (tcp/start-server message-handler {:host host :port port}))


(defn -main
  "Entry point for the broker.

  Runs the broker on given port (via cli arguments) and blocks the main thread"
  [& args]
  (let [{{port :port help? :help} :options :as opts} (parse-opts args cli-options)]
    (if help?
      (println (:summary opts))
      (netty/wait-for-close (start-server port)))))
