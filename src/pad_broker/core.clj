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
   ["-H" "--host HOSTNAME" "Hostname"
    :default "localhost"]
   ["-h" "--help"]])

(defn message-handler
  [s info]
  (println (str "New connection from " info)))

(defn -main
  "Entry point for the broker."
  [& args]
  (let [{{host :host port :port help? :help} :options :as opts} (parse-opts args cli-options)]
    (if help?
      (println (:summary opts))
      (do
        (println (str "Broker is up and running on " host ":" port))
        (println "Press Ctrl + C to close the application")
        (netty/wait-for-close
          (tcp/start-server message-handler {:host host :port port}))))))
