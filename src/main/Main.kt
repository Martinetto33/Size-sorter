package main

import main.data.Regions
import main.data.SizeNames
import main.model.ArticleDimension
import main.model.SizeSorter

fun main(args: Array<String>) {
    val dimensions = arrayOf(
        ArticleDimension(SizeNames.S),
        ArticleDimension(43),
        ArticleDimension(SizeNames.XL),
        ArticleDimension(40),
        ArticleDimension(44),
        ArticleDimension(SizeNames.M),
        ArticleDimension(12),
        ArticleDimension(Regions.IT, 35),
        ArticleDimension(Regions.IT, 43),
        ArticleDimension(Regions.FR, 12),
        ArticleDimension(Regions.UK, 50),
        ArticleDimension(SizeNames.XXL),
        ArticleDimension(Regions.IT, 50),
    )
    val sizeSorter = SizeSorter(dimensions)
    val orderedBySizeList = sizeSorter.sortByAbsoluteSize()
    val orderedByRegionsList = sizeSorter.sortByRegion()
    println("Ordered by size list: $orderedBySizeList")
    println("Ordered by region list: $orderedByRegionsList")
}