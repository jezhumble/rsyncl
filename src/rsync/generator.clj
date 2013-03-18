(ns rsync.generator
  (:use rsync.checksum))

(defn generate-signatures [input-stream buffer-size]
  (let [buffer (make-array Byte/TYPE buffer-size)]
    (loop [bytes-read (.read input-stream buffer)
           signature []]
      (if-not (= bytes-read -1)
        (recur (.read input-stream buffer)
               (conj signature [(weak-checksum buffer bytes-read (Math/pow 2 16)) (md5 buffer 0 bytes-read)])) ;NB fix weak-checksum for bytes-read < buffer-size
        signature))))
