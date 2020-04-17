library(data.tree)
library (maptree)
library(DiagrammeR)
library(igraph)
library(networkD3)

PATH <- "C:/Users/ZPP/Desktop/upperware/zpp-solver/testing_module/src/main/resources/"

nodes <- read.table(paste(PATH, "nodes1", sep=""), col.names =c("hash", "value", "visits", "failure_depth", "max_utility", "var_name"), sep=";")
edges <- read.table(paste(PATH, "tree1", sep=""), col.names =c("hash", "child_hash"), sep=";")

getRowByHash <- function(hash) {
  return (nodes[as.character(nodes$hash) == as.character(hash),])
}

buildTree2 <- function(root) {
  for (i in 1:length(edges$hash)[1]) {
    if (edges$hash[i] == root$name) {
      child <- root$AddChild(edges$child_hash[i])
      child$avgDepth <- getRowByHash(child$name)$failure_depth[1]
      child$utility <- getRowByHash(child$name)$max_utility[1]
      child$varName <- as.character(getRowByHash(child$name)$var_name[1])
      child$varValue <- as.character(getRowByHash(child$name)$value[1])
      buildTree2(child)
    }
  }
}

GetNodeLabel <- function(node) {
  depth <- as.character(as.integer(100*node$avgDepth))
  utility <- as.character(as.integer(1000*node$utility))
  return (paste(depth, utility, sep = "  "))
}


GetEdgeLabel <- function(node) {
  value <- as.character(node$varValue)
  return (paste(node$varName, value, sep ="\n"))
}


root <- Node$new(nodes$hash[1])
root$avgDepth <- 0
root$varName <- "ROOT"
buildTree2(root)
SetNodeStyle(root, fontname = 'helvetica', label = GetNodeLabel)
SetEdgeStyle(root, fontname = 'helvetica', label = GetEdgeLabel)
plot(root)



#Second version -- very computationally expensive for larger trees


getNodeLabel <- function(hash) {
  avgDepth <- getRowByHash(hash)$failure_depth[1]
  utility <- getRowByHash(hash)$max_utility[1]
  varName <- as.character(getRowByHash(hash)$var_name[1])
  varValue <- as.character(getRowByHash(hash)$value[1])
  return (paste(avgDepth, utility, varName, varValue, sep = " ;"))
}


tree <- ToDataFrameNetwork(root, "name", "value")
for (i in 1:dim(tree)[1]) {
  for (j in (1:2)) {
    tree[i,j] <- getNodeLabel(tree[i,j])
  }
}

simpleNetwork(tree[-3], fontSize = 12)
