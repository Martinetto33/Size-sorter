package main.model

import main.data.Regions
import main.data.convertToOtherRegionSize
import main.data.defaultRegion
import main.data.determineSizeFromName

interface ArticleComparator: Comparator<ArticleDimension> {
     override fun compare(a: ArticleDimension, b: ArticleDimension): Int
}

class SizeSorter(
    private val rawArticles: Array<ArticleDimension>,
    private val region: Regions = defaultRegion
) {
    fun convertToRegion(newRegion: Regions, articleDimension: ArticleDimension): ArticleDimension {
        if (articleDimension.region == newRegion) {
            return articleDimension
        }
        val newSize: Int = convertToOtherRegionSize(
            currentRegion = articleDimension.region,
            otherRegion = newRegion,
            currentRegionSize = articleDimension.size
        ) /*if (articleDimension.sizeName != null) {
            determineSizeFromName(articleDimension.sizeName, newRegion)
        } else {
            convertToOtherRegionSize(
                currentRegion = articleDimension.region,
                otherRegion = newRegion,
                currentRegionSize = articleDimension.size
            )
        }*/
        return ArticleDimension(newRegion, articleDimension.sizeName, newSize, areAllPropertiesInitialised = true)
    }

    /**
     * Sorts by numeric size, after converting the regions to [region]
     * if necessary.
     * */
    fun sortByAbsoluteSize(): List<ArticleDimension> {
        return this.rawArticles.sortedWith(object: ArticleComparator {
                override fun compare(a: ArticleDimension, b: ArticleDimension): Int {
                    return convertToRegion(region, a).size - convertToRegion(region, b).size
                }
            }
        )
    }

    fun sortByRegion(): List<ArticleDimension> {
        return this.rawArticles.toList()
            .groupBy { article -> article.region }
            .flatMap { it.value.sortedBy { article -> article.size } }
    }
}