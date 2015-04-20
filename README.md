# dataset-diff

A Clojure library designed to diff datasets and provide data on what needs to change in order to sync them.

## Usage

```clojure
(require 'dataset-diff.core)

(dataset-diff/diff [{:id 2, :name "foo"} {:id 3, :name "baz"}]
                   [{:id 1, :name "qux"} {:id 2, :name "bar"}])

;; => {:to-add [3], :to-remove [1], :to-update [2]}
```
