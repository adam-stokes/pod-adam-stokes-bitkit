(ns bitkit.package
  (:require [babashka.process :refer [$ check]]
            [clojure.string :as string]))

(defrecord RuntimeConfig [binary upgrade upgrade-system refresh install uninstall])
(def brew (RuntimeConfig. "brew" "upgrade" "upgrade" "update" "install -f -q" "uninstall"))
(def apt (RuntimeConfig. "apt" "install" "dist-upgrade" "update" "install -qyf" "remove -qyf"))

(defn pkg-runtime-config [var]
  (get-in runtime-config var))

(defn- os-name
  []
  (string/lower-case (System/getProperty "os.name")))

(defn- pkg-runtime
  []
  (condp #(string/includes? %2 %1) (os-name)
    "linux" apt
    "mac" brew
    ))

(defn install [pkgs]
  "install packages"
  (-> ($ ~(:binary (pkg-runtime)) ~(:install (pkg-runtime)) ~(string/join " " pkgs))
      check
      :out
      slurp))

(defn uninstall [pkgs]
  (-> ($ ~(pkg-runtime-prefix :action :uninstall) ~(string/join " " pkgs))
      check
      :out
      slurp))

(defn refresh []
  (-> ($ ~(pkg-runtime-prefix :action :refresh))
      check
      :out
      slurp))

(defn upgrade [pkgs]
  (-> ($ ~(pkg-runtime-prefix :action :upgrade) ~(string/join " " pkgs))
      check
      :out
      slurp))


(defn upgrade-system []
  (-> ($ ~(pkg-runtime-prefix :action :upgrade-system))
      check
      :out
      slurp))
