# Advent of Code 2018. Solutions in Clojure

Solutions to the problems in [here](https://adventofcode.com/2018). My timezone
sucks, so I'll be doing the solutions much later than when the problems are
released, but whatever.

The command:

``` text
lein run a b c
```

Will show solutions for the specified days (a b c ...). If no arguments are
given will show the solutions of every day so far implemented. Example:

``` text
$ lein run 1 5 3

  ========================================
 | Day | Star | Solution                  |
 |========================================|
 |  1  |  *   | 484                       |
 |     |  **  | 367                       |
 |  5  |  *   | 9288                      |
 |     |  **  | 5844                      |
 |  3  |  *   | 109716                    |
 |     |  **  | 124                       |
  ========================================
"Elapsed time: 2160.360176 msecs"
```

If you want to try your inputs, input files are stored at `resources/dayXX.txt`
