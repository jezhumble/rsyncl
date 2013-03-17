(ns rsync.checksum
  (:import [java.security MessageDigest]))

(def md5-digest (MessageDigest/getInstance "MD5"))

(defn sum-bytes [bytes]
  (apply + bytes))

(defn sum-product-bytes [bytes index]
  (+ (* (aget bytes index) (- (alength bytes) index))
     (if (= (+ index 1) (alength bytes))
       0
       (sum-product-bytes bytes (+ index 1)))))

(defn weak-checksum-a [bytes modulo]
  (rem (sum-bytes bytes) modulo))

(defn weak-checksum-b [bytes modulo]
  (rem (sum-product-bytes bytes 0) modulo))

(defn weak-checksum [bytes modulo]
  (+ (weak-checksum-a bytes modulo) (* modulo (weak-checksum-b bytes modulo))))

(defn md5 [bytes offset block-size]
  (do
    (.update md5-digest bytes offset block-size)
    (.digest md5-digest)
    ))

(defn byte-array-to-hex-string [bytes]
  (let [sb (StringBuilder. "0x")]
    (do
      (doseq [byte bytes]
        (.append sb (format "%02x" byte)))
      (.toString sb))))
