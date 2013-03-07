(ns rsync.checksum)

(defn sum-bytes [bytes]
  (apply + bytes))

(defn weak-checksum-a [bytes modulo]
   (rem (sum-bytes bytes) modulo))
