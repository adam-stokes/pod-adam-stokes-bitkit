(ns pod.adam-stokes.pkg
  {:no-doc true})

(def wtf (atom {}))

(defn install []
  (swap! wtf assoc "pkg" "install"))

(defn uninstall []
  (swap! wtf assoc "pkg" "UnInstall"))
