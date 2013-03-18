(ns rsync.generator-test
  (:use clojure.test
        rsync.generator))

; generator produces [[:filename :length :mtime :checksums] ... ]
; checksums take the form [[:fast-signature :strong-signature] ... ]

(def test-signatures
  [[1 2] [1 2] [1 2] [1 2]])

(deftest two-and-a-half-block-file-should-send-three-checksums
  (testing "Checksum for two-and-a-half block file"
    (let [in (java.io.FileInputStream. "/Users/jezhumble/Downloads/logo_go.png")]
      (is (= test-signatures (generate-signatures in 4096)))
      (.close in))))
