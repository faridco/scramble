# Scramble

## Development

```bash
clj -M:run
```

```bash
yarn watch
```


## Release
```bash
yarn release
mkdir classes
clj -e "(compile 'app.core)"
clojure -M:uberjar --main-class app.core
```
