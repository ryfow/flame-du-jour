# flame-du-jour

Translation: Flame of the Day

This small piece of code helps troubleshoot slow clojure load times.
It alters clojure.core/load to print load times to an output file of
stacks that that [FlameGraph](https://github.com/brendangregg/FlameGraph) can
turn into a pretty fire.

## Usage

`flame-du-jour.core/-main` takes an output-file and a code snippet to run.

If using leiningen, adding flame-du-jour to your user profile dependencies
makes it conveninent to use.
```clojure
{:user {:dependencies [[flame-du-jour "0.1.0"]]}}
```

Then, in a project I'm working in (for example refactor-nrepl), run
the following from a command line:

```bash
% lein run -m flame-du-jour.core flames.txt "(require 'refactor-nrepl.middleware :reload-all')"
```

That should create a stacks file in current working directory named `flames.txt`.

flamegraph.pl will turn the stacks file into a flames svg.

```
% ~/oss/FlameGraph/flamegraph.pl flames.txt > flames.svg
```

Most browsers support svg, so you can view it with `open -a /Applications/Google\ Chrome.app flames.svg`

![flames-example](https://ryfow.github.io/flame-du-jour/flames.svg)


## License

Copyright © 2017 Ryan Fowler

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
