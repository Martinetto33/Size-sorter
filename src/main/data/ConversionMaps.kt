package main.data

/**
 * This file will contain a series of maps used to store
 * the conversion from a size name (such as XL) to a number,
 * based on the different regions (IT, FR, UK ...).
 *
 * [conversionMaps] is a Map<Regions, Map<SizeNames, Int>>.
 * */

val conversionMaps = mapOf(
    // Source for the sizes: https://www.farfetch.com/style-guide/brands/french-brands-and-french-sizing-guide/
    Regions.IT to mapOf(
        38 to SizeNames.XXS,
        40 to SizeNames.XS,
        42 to SizeNames.S,
        44 to SizeNames.M,
        46 to SizeNames.L,
        48 to SizeNames.XL,
        50 to SizeNames.XXL
    ),
    Regions.FR to mapOf(
        34 to SizeNames.XXS,
        36 to SizeNames.XS,
        38 to SizeNames.S,
        40 to SizeNames.M,
        42 to SizeNames.L,
        44 to SizeNames.XL,
        46 to SizeNames.XXL
    ),
    Regions.UK to mapOf(
         6 to SizeNames.XXS,
         8 to SizeNames.XS,
        10 to SizeNames.S,
        12 to SizeNames.M,
        14 to SizeNames.L,
        16 to SizeNames.XL,
        18 to SizeNames.XXL
    )
)

fun maxSizeFromRegion(region: Regions): Int {
    return conversionMaps[region]?.keys?.max() ?: 0
}

fun minSizeFromRegion(region: Regions): Int {
    return conversionMaps[region]?.keys?.min() ?: 0
}

/**
 * Warning: if [size] is not listed in the conversion maps, this function
 * assigns a default value which is either [SizeNames.XXS] or [SizeNames.XXL].
 * This may lead to slight inaccuracies while ordering the articles.
 *
 * */
fun determineNameFromSize(size: Int, region: Regions): SizeNames? {
    val maxSize = maxSizeFromRegion(region)
    val minSize = minSizeFromRegion(region)
    if (size < minSize || size > maxSize) {
        return null
    }
    /**
     * Assumption made: every SizeName corresponds to an even number, and every
     * article in a SizeName category is either that even number or the odd
     * number immediately after it.
     * If this is not always the case, the logic in this lambda should be adjusted.
     * */
    return conversionMaps[region]!!
        .filter { it.key == size || it.key == size - 1 }
        .firstNotNullOf{ it.value }
}

fun determineSizeFromName(sizeName: SizeNames, region: Regions): Int {
    return conversionMaps[region]?.filter { it.value == sizeName }!!.firstNotNullOf { it.key }
}

/**
 * Converts a given size of a region into a size of another region.
 * This is necessary to allow comparison between articles with dimensions
 * that are not registered in the conversion maps.
 * */
fun convertToOtherRegionSize(currentRegion: Regions, otherRegion: Regions, currentRegionSize: Int): Int {
    if (currentRegion == otherRegion) {
        return currentRegionSize
    }
    val sizeName = determineNameFromSize(currentRegionSize, currentRegion)
    // If the size is a registered one (e.g. XS, S, ...), use that for the conversion
    if (sizeName != null) {
        return determineSizeFromName(sizeName, otherRegion) + (currentRegionSize % 2)
    }
    val maxSize = maxSizeFromRegion(currentRegion)
    val minSize = minSizeFromRegion(currentRegion)
    if (currentRegionSize < minSize) {
        val interval = minSize - currentRegionSize
        return determineSizeFromName(SizeNames.XXS, otherRegion) - interval
    }
    if (currentRegionSize > maxSize) {
        val interval = currentRegionSize - maxSize
        return determineSizeFromName(SizeNames.XXL, otherRegion) + interval
    }
    throw IllegalStateException("Can't convert size $currentRegionSize in region $currentRegion to size of $otherRegion")
}