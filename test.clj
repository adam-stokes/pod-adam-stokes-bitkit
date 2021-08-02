#!/usr/bin/env bb

(require '[babashka.pods :as pods])

(if (= "executable" (System/getProperty "org.graalvm.nativeimage.kind"))
  (pods/load-pod "./pod-stokachu-bitkit")
  (pods/load-pod ["clojure" "-M" "-m" "pod.stokachu.bitkit"]))

(require '[pod.stokachu.bitkit.package :as pkg])

(prn (pkg/install '(vim babashka)))
(prn (pkg/update))

(when-not (= "executable" (System/getProperty "org.graalvm.nativeimage.kind"))
  (shutdown-agents)
  (System/exit 0))
