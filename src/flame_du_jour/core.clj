(ns flame-du-jour.core
  (:require [clojure.string :as str]))

(def ^:dynamic *path* [])
(def ^:dynamic *flame-out*)
(def last-print (atom 0))

(defn -main [output-file code]
  (alter-var-root
   #'clojure.core/load
   (fn [orig]
     (fn [& paths]
       (binding [*path* (conj *path* (str/join "|" (mapv #(subs % 1) paths)))]
         (let [load-result (apply orig paths)]
           (binding [*out* *flame-out*]
             (println  (-> (str/join ";" *path*)
                           (str/replace #"/" "."))
                       (/ (- (System/nanoTime) @last-print) 1000000.0))
             (reset! last-print (System/nanoTime))
             (flush))
           load-result)))))

  (try
    (reset! last-print (System/nanoTime))
    (binding [*flame-out* (clojure.java.io/writer output-file)]
      (eval (read-string code)))
    (catch Exception e
      (.printStackTrace e))
    (finally (shutdown-agents))))
