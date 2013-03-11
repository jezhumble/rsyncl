(ns rsync.checksum-test
  (:use clojure.test
        rsync.checksum))

(def my-byte-array (byte-array (map byte [24, 125, 69])))

(deftest weak-checksum-match
  (testing "Weak Checksum"
    (is (= (+ 24 125 69) (sum-bytes my-byte-array)))
    (is (= (+ (* 3 24) (* 2 125) 69) (sum-product-bytes my-byte-array 0)))
    (is (= 18 (weak-checksum-a my-byte-array 200)))
    (is (= 191 (weak-checksum-b my-byte-array 200)))
    ))
