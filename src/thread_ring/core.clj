(ns thread-ring.core
  (:require [criterium.core :refer :all]))

(defmacro compile-if
  "Evaluate `exp` and if it returns logical true and doesn't error, expand to
  `then`.  Else expand to `else`."
  ([exp then]
     `(compile-if ~exp ~then nil))
  ([exp then else]
     (if (try (eval exp)
              (catch Throwable _ false))
       then
       else)))

(defn parse-long
  ([s] (parse-long s 0))
  ([s default]
     (try
       (Long/parseLong s)
       (catch Throwable _
         default))))

(compile-if (resolve 'co.paralleluniverse.strands.Strand)
  (require '[co.paralleluniverse.pulsar.async :as async])
  (require '[clojure.core.async :as async]))

(defn thread-ring [size n]
  {:pre [(> size 1)]}
  (let [result (async/chan)
        [head :as ring] (vec (repeatedly size #(async/chan 1)))]
    (dotimes [i size]
      (let [id (inc i)
            in (ring i)
            out (ring (mod id size))]
        (async/go (while true
                    (let [m (async/<! in)]
                      (if (> m 0)
                        (async/>! out (dec m))
                        (async/>! result id)))))))
    (async/>!! head n)
    (async/<!! result)))

(defn -main [& [ssize sn :as args]]
  (let [size (parse-long ssize 503)
        n (parse-long sn 500000)
        thunk #(thread-ring size n)]
    (println (str "(thread-ring " size " " n ") => " (thunk)))
    (with-progress-reporting (bench (thunk)))))
