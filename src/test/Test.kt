package test

import junit.framework.TestCase.assertEquals
import main.data.Regions
import main.data.defaultRegion
import main.model.ArticleDimension
import main.model.SizeSorter
import org.junit.Test

class Test {
    @Test
    fun testConversions() {
        val ss = SizeSorter(arrayOf())
        val newArticle = ss.convertToRegion(Regions.IT, ArticleDimension(Regions.FR, 12))
        assertEquals(ArticleDimension(Regions.IT, 16), newArticle)
        val ukArticle = ArticleDimension(Regions.UK, 13)
        val convUKArticle = ss.convertToRegion(Regions.IT, ukArticle)
        assertEquals(ArticleDimension(Regions.IT, 45), convUKArticle)
    }
    @Test
    fun testSortingByAbsoluteSize() {
        val articles = arrayOf(
            ArticleDimension(0),
            ArticleDimension(2),
            ArticleDimension(1),
            ArticleDimension(-3)
        )
        val ss = SizeSorter(articles, defaultRegion)
        assertEquals(
            listOf(
                ArticleDimension(-3),
                ArticleDimension(0),
                ArticleDimension(1),
                ArticleDimension(2)
            ),
            ss.sortByAbsoluteSize()
        )
    }

    @Test
    fun testSortingByAbsoluteSize2() {
        val articles = arrayOf(
            ArticleDimension(Regions.UK, 13),
            ArticleDimension(Regions.IT, 11),
            ArticleDimension(Regions.FR, 12),
        )
        val ss = SizeSorter(articles, defaultRegion)
        assertEquals(
            listOf(
                ArticleDimension(Regions.IT, 11),
                ArticleDimension(Regions.FR, 12), // size 12 in France should correspond to size 16 in Italy
                ArticleDimension(Regions.UK, 13) // size 13 in the UK should correspond to size 45 in Italy
            ),
            ss.sortByAbsoluteSize()
        )
    }

    @Test
    fun testSortingByRegion() {
        val articles = arrayOf(
            ArticleDimension(Regions.UK, 19),
            ArticleDimension(Regions.IT, 11),
            ArticleDimension(Regions.FR, 12),
            ArticleDimension(Regions.UK, 13),
            ArticleDimension(Regions.IT, 11),
            ArticleDimension(Regions.FR, 15),
        )
        val ss = SizeSorter(articles, defaultRegion)
        assertEquals(
            listOf(
                ArticleDimension(Regions.UK, 13),
                ArticleDimension(Regions.UK, 19),
                ArticleDimension(Regions.IT, 11),
                ArticleDimension(Regions.IT, 11),
                ArticleDimension(Regions.FR, 12),
                ArticleDimension(Regions.FR, 15),
            ),
            ss.sortByRegion()
        )
    }
}