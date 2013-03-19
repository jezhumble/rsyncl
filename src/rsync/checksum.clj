(ns rsync.checksum
  (:import [java.security MessageDigest]))

(def md5-digest (MessageDigest/getInstance "MD5"))

(defn sum-bytes [bytes index length]
  (loop [sum 0
         sum-product 0
         i index]
    (if (< i length)
      (recur (+ sum (bit-and (aget bytes i) 0xff))
             (+ sum-product (* (bit-and (aget bytes i) 0xff) (- length i)))
             (inc i))
      [sum, sum-product])))

(defn weak-checksum [bytes length modulo]
  (let [[sum, sum-product] (sum-bytes bytes 0 length)]
    (+ (rem sum modulo) (* modulo (rem sum-product modulo)))))

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
