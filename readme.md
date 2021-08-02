# pod-stokachu-bitkit

## API

TBD

## Example

```clojure
(require '[babashka.pods :as pods])

(pods/load-pod 'pod.stokachu/bitkit "0.0.1")

(require '[pod.stokachu.bitkit.package :as pkg])

(prn (pkg/install ["emacs" "babashka"]))
(prn (pkg/update))
(prn (pkg/uninstall ["emacs"])
(prn (pkg/upgrade-system))
```

## License

Copyright &copy; 2021 Adam Stokes <adam.stokes@gmail.com>

MIT
