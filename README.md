Java Network Analyzer
=====================

Java Network Analyzer provides a collection of [social network
analysis](http://en.wikipedia.org/wiki/Social_network_analysis) algorithms.
These algorithms are implemented on mathematical graphs using the
[JGraphT](https://github.com/jgrapht/jgrapht) library.

#### Currently supported

Augmented [BFS](http://en.wikipedia.org/wiki/Breadth-first_search),
[DFS](http://en.wikipedia.org/wiki/Depth-first_search) and
[Dijkstra](http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) algorithms are
used to compute:

* [Betweenness centrality](http://en.wikipedia.org/wiki/Betweenness_centrality)
* [Closeness centrality](http://en.wikipedia.org/wiki/Centrality#Closeness_centrality)
* [Strahler stream order](http://en.wikipedia.org/wiki/Strahler_number) (for
  mathematical trees)

#### Graph types
The underlying graph may be directed, edge-reversed or undirected, and edges may
or may not have weights.
