(ns rsync.checksum-test
  (:use clojure.test
        rsync.checksum))

(def my-byte-array (byte-array (map byte [24, 125, 69])))

(deftest weak-checksum-match
  (testing "Weak Checksum"
    (is (= (+ 24 129) ((sum-bytes (byte-array (map byte [24 -127])) 0 2) 0))) ;make sure we and with 0xff
    (is (= (+ 24 125 69) ((sum-bytes my-byte-array 0 3) 0)))
    (is (= (+ (* 3 24) (* 2 125) 69) ((sum-bytes my-byte-array 0 3) 1)))
    (is (= 18 (rem ((sum-bytes my-byte-array 0 3) 0) 200)))
    (is (= 191 (rem ((sum-bytes my-byte-array 0 3) 1) 200)))
    (is (= 38218 (weak-checksum my-byte-array 3 200)))
    ))

(deftest md5-match
  (testing "md5"
    (is (= "0x70b606719d008c611b66a695bd5aad5c" (byte-array-to-hex-string (md5 my-byte-array 0 2))))
    (is (= "0x3a3ea00cfc35332cedf6e5e9a32e94da" (byte-array-to-hex-string (md5 my-byte-array 2 1))))))
