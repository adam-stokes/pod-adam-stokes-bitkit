(ns bitkit.package
  (:refer-clojure :exclude [update])
  (:require [babashka.process :as p]
            [clojure.string :as string]))

(defrecord RuntimeConfig [binary upgrade upgrade-system update install uninstall])
(def brew (RuntimeConfig.
           "brew"
           "upgrade"
           "upgrade"
           "update"
           "install"
           "uninstall"))
(def apt (RuntimeConfig.
          "apt"
          "install"
          "dist-upgrade"
          "update"
          "install -qyf"
          "remove -qyf"))

(defn- os-name
  []
  (string/lower-case (System/getProperty "os.name")))

(defn- pkg-runtime
  []
  (condp #(string/includes? %2 %1) (os-name)
    "linux" apt
    "mac" brew
    ))

(defn- build-cmd
  [executor action & [opts]]
  "builds a command to use in public methods"
    (concat [(:binary executor) (action executor)] opts))

(defn- execute!
  [cmds]
  (let [result @(p/process cmds {:out :inherit :err :inherit})]
    (when-not (zero? (:exit result))
      (throw (ex-info "command execution failed"
                      {:cmd cmds
                       :exit (:exit result)})))))

(defn install
  [pkgs]
  "install packages"
  (execute! (build-cmd (pkg-runtime) :install pkgs)))

(defn uninstall [pkgs]
  (execute! (build-cmd (pkg-runtime) :uninstall pkgs)))

(defn update []
  (execute! (build-cmd (pkg-runtime) :update)))

(defn upgrade [pkgs]
  (execute! (build-cmd (pkg-runtime) :upgrade pkgs)))

(defn upgrade-system []
  (execute! (build-cmd (pkg-runtime) :upgrade-system)))
