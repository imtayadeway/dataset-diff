(ns dataset-diff.core-test
  (:require [clojure.test :refer :all]
            [dataset-diff.core :refer :all]))

(deftest test-to-add
  (is (=
       {:to-add [2], :to-remove [], :to-update []}
       (diff [{:id 1} {:id 2}] [{:id 1}]))))

(deftest test-to-remove
  (is (=
       {:to-add [] :to-remove [2], :to-update []}
       (diff [{:id 1}] [{:id 1} {:id 2}]))))

(deftest test-to-update
  (is (=
       {:to-add [], :to-remove [], :to-update [2]}
       (diff [{:id 1, :name "foo"} {:id 2, :name "bar"}]
             [{:id 1, :name "foo"} {:id 2, :name "baz"}]))))

(deftest test-diff
  (is (=
       {:to-add [3], :to-remove [1], :to-update [2]}
       (diff [{:id 0, :name "fred"} {:id 2, :name "foo"} {:id 3, :name "baz"}]
             [{:id 0, :name "fred"} {:id 1, :name "qux"} {:id 2, :name "bar"}]))))
