
library(data.tree)
library (maptree)
library(DiagrammeR)

PATH <- "C:/Users/ZPP/Desktop/tylkonachwile/upperware/zpp-solver/testing_module/src/main/resources/"

nodes <- read.table(paste(PATH, "nodes2", sep=""), col.names =c("hash", "value", "visits", "failure_depth", "max_utility", "var_name"), sep=";")
edges <- read.table(paste(PATH, "tree2", sep=""), col.names =c("hash", "child_hash"), sep=";")

getRowByHash <- function(hash) {
  return (nodes[as.character(nodes$hash) == as.character(hash),])
}

buildTree2 <- function(root) {
  for (i in 1:length(edges$hash)[1]) {
    if (edges$hash[i] == root$name) {
      child <- root$AddChild(edges$child_hash[i])
      child$avgDepth <- getRowByHash(child$name)$failure_depth[1]
      child$varName <- as.character(getRowByHash(child$name)$var_name[1])
      buildTree2(child)
    }
  }
}

GetNodeLabel <- function(node) {
  return (node$avgDepth)
}
  

GetEdgeLabel <- function(node) {
  return (node$varName)
}
  #if (!node$isRoot && node$parent$type == 'chance') {
    #label = paste0(node$name, " (", node$p, ")")

    #  } else {
   # label = node$name
  #}
 # return (label)
#}

#GetNodeShape <- function(node) switch(node$type, decision = "box", chance = "circle", terminal = "none")


#SetEdgeStyle(jl, fontname = 'helvetica', label = GetEdgeLabel)
#SetNodeStyle(jl, fontname = 'helvetica', label = GetNodeLabel, shape = GetNodeShape)

root <- Node$new(nodes$hash[1])
root$avgDepth <- 0
root$varName <- "ROOT"
buildTree2(root)
SetNodeStyle(root, fontname = 'helvetica', label = GetNodeLabel)
SetEdgeStyle(root, fontname = 'helvetica', label = GetEdgeLabel)
plot(root)

