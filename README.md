# Cooperative Network-wide Flow Selection (CFS)
his repository contains the source code of the "Routing-Oblivious Network-Wide Measurements", by Ran Ben-Basat, Gil Einziger, Bilal Tayh

Implementation was done by bilal tayh (tayh[at]post.bgu.ac.il), 2019.


# How to compile and run

compile: javac main.java

run: java main trace-path Topology cells-num
(trace-path is the path of the trace, the trace should be a .txt file),
(Topology: 0 for geant topology and 1 for fattree),
(cells-num the size of Samples Per NMP).
