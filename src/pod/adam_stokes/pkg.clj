(ns pod.adam-stokes.pkg
  (:require [babashka.process :refer [$ sh check]]
            [clojure.string :as string]))

(defn install [pkgs]
  "install packages"
  (-> ($ echo install ~(string/join " " pkgs))
      check
      :out
      slurp))

(defn uninstall []
  (-> ($ echo uninstall)
      check
      :out
      slurp))

(defn refresh []
  (-> ($ echo brew update)
      check
      :out
      slurp))
