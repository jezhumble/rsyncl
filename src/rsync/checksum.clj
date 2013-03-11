(ns rsync.checksum)

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
