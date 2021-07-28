#!/usr/bin/env bb

(require '[babashka.pods :as pods])

(if (= "executable" (System/getProperty "org.graalvm.nativeimage.kind"))
  (pods/load-pod "./pod-adam-stokes-bitkit")
  (pods/load-pod ["clojure" "-M" "-m" "pod.adam-stokes.bitkit"]))

(require '[pod.adam-stokes.bitkit.pkg :as pkg])

(prn (pkg/install))
(prn (pkg/uninstall))

(when-not (= "executable" (System/getProperty "org.graalvm.nativeimage.kind"))
  (shutdown-agents)
  (System/exit 0))
