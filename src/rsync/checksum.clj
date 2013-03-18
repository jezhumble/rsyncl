(ns rsync.checksum
  (:import [java.security MessageDigest]))

(def md5-digest (MessageDigest/getInstance "MD5"))

(defn sum-bytes [bytes index length]
  (loop [total 0
         i index]
    (if (< i length)
      (recur (+ total (bit-and (aget bytes i) 0xff))
             (inc i))
      total)))

(defn sum-product-bytes [bytes index length]
  (loop [total 0
         i index]
    (if (< i length)
      (recur (+ total (* (bit-and (aget bytes i) 0xff) (- length i)))
             (inc i))
      total)))
           
(defn weak-checksum-a [bytes length modulo]
  (rem (sum-bytes bytes 0 length) modulo))

(defn weak-checksum-b [bytes length modulo]
  (rem (sum-product-bytes bytes 0 length) modulo))

(defn weak-checksum [bytes length modulo]
  (+ (weak-checksum-a bytes length modulo) (* modulo (weak-checksum-b bytes length modulo))))

(defn md5 [bytes offset block-size]
  (do
    (.update md5-digest bytes offset block-size)
    (.digest md5-digest)))

(defn byte-array-to-hex-string [bytes]
  (let [sb (StringBuilder. "0x")]
    (do
      (doseq [byte bytes]
        (.append sb (format "%02x" byte)))
      (.toString sb))))
