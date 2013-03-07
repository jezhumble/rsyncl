(ns rsync.checksum-test
  (:use clojure.test
        rsync.checksum))

(def my-byte-array (byte-array (map byte [0x18, 0x7D, 0x45])))

(deftest weak-checksum-match
  (testing "Weak Checksum"
    (is (= 218 (sum-bytes my-byte-array)))
    (is (= 18 (weak-checksum-a my-byte-array 200)))))
