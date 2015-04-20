(ns dataset-diff.core)
(require 'clojure.set)

(defn set-of-ids
  "Get the set of ids from a dataset"
  [dataset]
  (set (mapv :id dataset)))

(defn id-difference
  "Get a vector of ids from set 1 that are not in set 2"
  [ds1 ds2]
  (vec (apply clojure.set/difference (map set-of-ids [ds1 ds2]))))

(defn rows-match?
  "Determines if two rows (i.e. two maps) are identical"
  [row1, row2]
  (= row1 row2))

(defn to-update
  "Get the ids from rows that do not match in two datasets"
  [ds1 ds2]
  (let [zip (map vector ds1 ds2)]
    (mapv
     #((first %) :id)
     (remove
      #(rows-match? (first %) (second %))
      zip))))

(defn reject-rows
  [dataset ids]
  (remove #(some #{(:id %)} ids) dataset))

(defn sort-dataset
  "Sort a dataset by id"
  [dataset]
  (sort-by :id dataset))

(defn diff
  "Take two datasets and return the ids from rows that need to be
  added, those that need to be removed, and those that need to be
  updated."
  [ds1 ds2]
  (let [sorted-ds1 (sort-dataset ds1)
        sorted-ds2 (sort-dataset ds2)
        to-add (id-difference sorted-ds1 sorted-ds2)
        to-remove (id-difference sorted-ds2 sorted-ds1)
        updatable-ds1 (reject-rows sorted-ds1 to-add)
        updatable-ds2 (reject-rows sorted-ds2 to-remove)]
  {:to-add to-add,
   :to-remove to-remove,
   :to-update (to-update updatable-ds1 updatable-ds2)}))
