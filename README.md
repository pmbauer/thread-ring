# thread-ring

Benchmark comparison between [core.async](https://github.com/clojure/core.async) and [Quasar/Pulsar](https://github.com/puniverse/pulsar).

## Methodology
Classic thread-ring benchmark with criterium and leiningen `:jvm-opts ^:replace`.

## Sample
_Processor: Intel® Core™ i7-4600U CPU  
Memory: 8GB_

```
$ lein core.async 503 500000
(thread-ring 503 500000) => 19
...
Evaluation count : 60 in 60 samples of 1 calls.
             Execution time mean : 1.110358 sec
    Execution time std-deviation : 25.026825 ms
   Execution time lower quantile : 1.066998 sec ( 2.5%)
   Execution time upper quantile : 1.137509 sec (97.5%)
                   Overhead used : 1.844661 ns

Found 4 outliers in 60 samples (6.6667 %)
	low-severe	 1 (1.6667 %)
	low-mild	 3 (5.0000 %)
 Variance from outliers : 10.9690 % Variance is moderately inflated by outliers

$ lein pulsar 503 500000
(thread-ring 503 500000) => 19
...
Evaluation count : 120 in 60 samples of 2 calls.
             Execution time mean : 594.285218 ms
    Execution time std-deviation : 2.693540 ms
   Execution time lower quantile : 590.139810 ms ( 2.5%)
   Execution time upper quantile : 599.500780 ms (97.5%)
                   Overhead used : 1.843652 ns
```

## Observations

For this benchmark, Pulsar pegged the CPU while core.async kept
utilization at a near-steady 50%.  Pulsar's execution time advantage
reflects this.
